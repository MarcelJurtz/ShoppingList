package com.jurtz.marcel.shoppinglist;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
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

    public ShoppingListPresenter(IDetailView view, int shoppingListId) {
        this.view = view;
        shoppingListItems = new ArrayList<ShoppingListItem>();
        shoppingListItemAdapter = new ShoppingListItemAdapter(shoppingListItems);
        this.shoppingListId = shoppingListId;
    }

    @Override
    public void onShoppingListItemClick(int position) {
        final ShoppingListItem item = shoppingListItems.get(position);
        shoppingListItems.remove(item);
        AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao().deleteItem(item);
        reloadAdapter();
        view.loadRestoreSnackbar(item);
    }

    @Override
    public void onRestoreSnackbarClick(ShoppingListItem item) {
        shoppingListItems.add(item);
        AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao().insertItem(item);
        reloadAdapter();
    }

    @Override
    public void onFloatingActionButtonClick() {
        view.loadNewEntryDialog();
    }

    @Override
    public void onSortButtonClick() {
        currentlySortedByTimeStamp = !currentlySortedByTimeStamp;
        reloadAdapter();
    }

    @Override
    public void onEntryDialogConfirmation(String input) {
        ShoppingListItem item = new ShoppingListItem();
        item.description = input;
        item.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        item.listId = shoppingListId;
        AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao().insertItem(item);
        shoppingListItems.add(item);
        reloadAdapter();
    }

    @Override
    public void onResume() {
        reloadAdapter();
        view.initAdapter(shoppingListItemAdapter);
    }

    @Override
    public void onCreate() {
        reloadAdapter();
        view.initAdapter(shoppingListItemAdapter);
    }

    private void reloadAdapter() {
        shoppingListItems = AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao().getAllForShoppingList(shoppingListId);

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
            AppDatabase.getAppDatabase(view.getContext()).shoppingListDao().deleteListById(shoppingListId);
        }
    }

    @Override
    public void onAddNewItemButtonClick() {
        view.loadNewEntryDialog();
    }
}
