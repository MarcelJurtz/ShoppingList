package com.jurtz.marcel.sist;

import android.content.Intent;
import android.os.AsyncTask;

import com.jurtz.marcel.sist.contract.IListPresenter;
import com.jurtz.marcel.sist.contract.IListView;
import com.jurtz.marcel.sist.database.AppDatabase;
import com.jurtz.marcel.sist.database.ShoppingListDao;
import com.jurtz.marcel.sist.model.ShoppingList;
import com.jurtz.marcel.sist.model.ShoppingListAdapter;
import com.jurtz.marcel.sist.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListPresenter implements IListPresenter {

    private IListView view;
    List<ShoppingList> shoppingLists;
    boolean currentlySortedByTimeStamp;
    ShoppingListAdapter shoppingListAdapter;

    private final ShoppingListDao shoppingListDao;

    public ListPresenter(IListView view) {
        this.view = view;
        shoppingLists = new ArrayList<>();
        shoppingListAdapter = new ShoppingListAdapter(shoppingLists);
        shoppingListDao = AppDatabase.getAppDatabase(view.getContext()).shoppingListDao();
    }


    @Override
    public void onListClick(int position) {
        Intent intent = new Intent(view.getContext(), DetailView.class);
        intent.putExtra("shoppinglist_id", shoppingLists.get(position).id);
        intent.putExtra("shoppinglist_description", shoppingLists.get(position).description);
        view.loadIntent(intent);
    }



    @Override
    public void onFabClick() {
        view.loadNewItemDialog();
    }

    @Override
    public void onSortClick() {
        currentlySortedByTimeStamp = !currentlySortedByTimeStamp;
        refreshGui();
    }

    @Override
    public void onAddDialogConfirm(String input) {
        ShoppingList list = new ShoppingList();
        list.description = input;
        list.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        list.itemCount = 0;

        new AddListTask(shoppingListDao, list).execute();
    }

    @Override
    public void onEditDialogConfirm(ShoppingListItem item) { // TODO Implementation
        //ShoppingList list = new ShoppingList();
        //list.description = input;
        //list.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        //list.itemCount = 0;

        //new AddListTask(shoppingListDao, list).execute();
    }

    @Override
    public void onResume() {
        new GetAllListsTask(shoppingListDao).execute();
    }

    private void refreshGui() {
        if(currentlySortedByTimeStamp) {
            Collections.sort(shoppingLists, new Comparator<ShoppingList>() {
                @Override
                public int compare(ShoppingList lhs, ShoppingList rhs) {
                    return lhs.description.compareTo(rhs.description);
                }
            });
        } else {
            Collections.sort(shoppingLists, new Comparator<ShoppingList>() {
                @Override
                public int compare(ShoppingList lhs, ShoppingList rhs) {
                    Integer left = new Integer(lhs.timestampSeconds);
                    Integer right = new Integer(rhs.timestampSeconds);
                    return left.compareTo(right);
                }
            });
        }

        shoppingListAdapter.setShoppingLists(shoppingLists);
        shoppingListAdapter.notifyDataSetChanged();

        view.initAdapter(shoppingListAdapter);
    }

    public class GetAllListsTask extends AsyncTask<Void, Void, Void> {

        private final ShoppingListDao listDao;

        List<ShoppingList> lists = new ArrayList<ShoppingList>();

        public GetAllListsTask(ShoppingListDao dao) {
            super();
            listDao = dao;
        }

        protected Void doInBackground(Void... params) {
            lists = listDao.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            shoppingLists = lists;
            refreshGui();
        }
    }

    public class AddListTask extends AsyncTask<Void, Void, Void> {
        private final ShoppingListDao listDao;
        private final ShoppingList list;

        public AddListTask(ShoppingListDao dao, ShoppingList list) {
            super();
            listDao = dao;
            this.list = list;
        }

        protected Void doInBackground(Void... params) {
            listDao.insertList(list);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new GetAllListsTask(shoppingListDao).execute();
        }
    }
}