package com.jurtz.marcel.shoppinglist;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShoppingListPresenter implements IDetailPresenter {

    private IDetailView view;
    List<ShoppingListItem> shoppingListItems;
    boolean currentlySortedByTimeStamp;
    ShoppingListItemAdapter shoppingListItemAdapter;

    public ShoppingListPresenter(IDetailView view) {
        this.view = view;
        shoppingListItems = new ArrayList<ShoppingListItem>();
        shoppingListItemAdapter = new ShoppingListItemAdapter(shoppingListItems);
    }

    @Override
    public void onShoppingListItemClick(int position) {
        final ShoppingListItem item = shoppingListItems.get(position);
        shoppingListItems.remove(item);
        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().deleteItem(item);
        reloadAdapter();

        // Show snackbar to restore entry
        Snackbar.make(parentLayout, getResources().getString(R.string.snackbar_information), Snackbar.LENGTH_LONG).
                setAction(getResources().getString(R.string.snackbar_restore), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shoppingListItems.add(item);
                        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().insertItem(item);
                        reloadAdapter();
                    }

                }).show();
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
        AppDatabase.getAppDatabase(view.getContext()).shoppingListDao().insertList(item);
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
        shoppingListItems = AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao().getAllForShoppingList();

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
}
