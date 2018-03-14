package com.jurtz.marcel.shoppinglist;

import android.os.AsyncTask;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.database.ShoppingListDao;
import com.jurtz.marcel.shoppinglist.database.ShoppingListItemDao;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShoppingListPresenter implements IDetailPresenter {

    private IDetailView view;
    private List<ShoppingListItem> shoppingListItems;
    private boolean currentlySortedByTimeStamp;
    private ShoppingListItemAdapter shoppingListItemAdapter;
    private int shoppingListId;
    private final ShoppingListItemDao shoppingListItemDao;
    private final ShoppingListDao shoppingListDao;

    public ShoppingListPresenter(IDetailView view, int shoppingListId) {
        this.view = view;
        shoppingListItems = new ArrayList<ShoppingListItem>();
        shoppingListItemAdapter = new ShoppingListItemAdapter(shoppingListItems);
        this.shoppingListId = shoppingListId;
        shoppingListItemDao = AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao();
        shoppingListDao = AppDatabase.getAppDatabase(view.getContext()).shoppingListDao();
    }

    @Override
    public void onShoppingListItemClick(int position) {
        final ShoppingListItem item = shoppingListItems.get(position);
        shoppingListItems.remove(item);


        new DeleteItemTask(shoppingListItemDao, item).execute();
    }

    @Override
    public void onRestoreSnackbarClick(final ShoppingListItem item) {
        new AddItemTask(shoppingListItemDao, item).execute();
    }

    @Override
    public void onFloatingActionButtonClick() {
        view.loadNewEntryDialog();
    }

    @Override
    public void onSortButtonClick() {
        currentlySortedByTimeStamp = !currentlySortedByTimeStamp;
        refreshGui();
    }

    @Override
    public void onEntryDialogConfirmation(String input) {
        final ShoppingListItem item = new ShoppingListItem();
        item.description = input;
        item.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        item.listId = shoppingListId;

        new AddItemTask(shoppingListItemDao, item).execute();
    }

    @Override
    public void onResume() {
        new GetAllItemsTask(shoppingListItemDao).execute();
        refreshGui();
        view.initAdapter(shoppingListItemAdapter);
    }

    private void refreshGui() {
        if(currentlySortedByTimeStamp) {
            Collections.sort(shoppingListItems, new Comparator<ShoppingListItem>() {
                @Override
                public int compare(ShoppingListItem lhs, ShoppingListItem rhs) {
                    return lhs.description.compareTo(rhs.description);
                }
            });
        } else {
            Collections.sort(shoppingListItems, new Comparator<ShoppingListItem>() {
                @Override
                public int compare(ShoppingListItem lhs, ShoppingListItem rhs) {
                    Integer left = new Integer(lhs.timestampSeconds);
                    Integer right = new Integer(rhs.timestampSeconds);
                    return left.compareTo(right);
                }
            });
        }

        shoppingListItemAdapter.setShoppingListItems(shoppingListItems);
        shoppingListItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        if(shoppingListItems.size() == 0) {
            new DeleteListTask(shoppingListDao).execute();
        }
    }

    public class GetAllItemsTask extends AsyncTask<Void, Void, Void> {

        private final ShoppingListItemDao itemDao;

        List<ShoppingListItem> lists = new ArrayList<ShoppingListItem>();

        public GetAllItemsTask(ShoppingListItemDao dao) {
            super();
            itemDao = dao;
        }

        protected Void doInBackground(Void... params) {
            lists = itemDao.getAllForShoppingList(shoppingListId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            shoppingListItems = lists;
            refreshGui();
        }
    }

    public class AddItemTask extends AsyncTask<Void, Void, Void> {
        private final ShoppingListItemDao itemDao;
        private final ShoppingListItem item;

        public AddItemTask(ShoppingListItemDao dao, ShoppingListItem item) {
            super();
            itemDao = dao;
            this.item = item;
        }

        protected Void doInBackground(Void... params) {
            itemDao.insertItem(item);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new SetListItemCountTask(shoppingListDao, shoppingListItems.size() +1).execute();
        }
    }

    public class DeleteItemTask extends AsyncTask<Void, Void, Void> {
        private final ShoppingListItemDao itemDao;
        private final ShoppingListItem item;

        public DeleteItemTask(ShoppingListItemDao dao, ShoppingListItem item) {
            super();
            itemDao = dao;
            this.item = item;
        }

        protected Void doInBackground(Void... params) {
            itemDao.deleteItem(item);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            view.loadRestoreSnackbar(item);
            new SetListItemCountTask(shoppingListDao, shoppingListItems.size()).execute();
        }
    }


    public class SetListItemCountTask extends AsyncTask<Void, Void, Void> {
        private final ShoppingListDao listDao;
        private final int count;

        public SetListItemCountTask(ShoppingListDao dao, int count) {
            super();
            listDao = dao;
            this.count = count;
        }

        protected Void doInBackground(Void... params) {
            listDao.updateListItemCount(count, shoppingListId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new GetAllItemsTask(shoppingListItemDao).execute();
        }
    }

    public class DeleteListTask extends AsyncTask<Void, Void, Void> {
        private final ShoppingListDao listDao;

        public DeleteListTask(ShoppingListDao dao) {
            super();
            listDao = dao;
        }

        protected Void doInBackground(Void... params) {
            listDao.deleteListById(shoppingListId);
            return null;
        }
    }

}
