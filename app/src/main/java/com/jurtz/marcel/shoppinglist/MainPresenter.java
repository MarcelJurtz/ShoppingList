package com.jurtz.marcel.shoppinglist;

import android.content.Intent;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPresenter implements IMainPresenter {

    private IMainView view;
    List<ShoppingList> shoppingLists;
    boolean currentlySortedByTimeStamp;
    ShoppingListAdapter shoppingListAdapter;


    public MainPresenter(IMainView view) {
        this.view = view;
        shoppingLists = new ArrayList<>();
        shoppingListAdapter = new ShoppingListAdapter(shoppingLists);
    }


    @Override
    public void onShoppingListClick(int position) {
        Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
        intent.putExtra("shoppinglist_id", shoppingLists.get(position).id);
        intent.putExtra("shoppinglist_description", shoppingLists.get(position).description);
        view.loadIntent(intent);
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
        ShoppingList list = new ShoppingList();
        list.description = input;
        list.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        AppDatabase.getAppDatabase(view.getContext()).shoppingListDao().insertList(list);
        shoppingLists.add(list);
        reloadAdapter();
    }

    @Override
    public void onResume() {
        reloadAdapter();
        view.initAdapter(shoppingListAdapter);
    }

    @Override
    public void onCreate() {
        reloadAdapter();
        view.initAdapter(shoppingListAdapter);
    }

    private void reloadAdapter() {
        shoppingLists = AppDatabase.getAppDatabase(view.getContext()).shoppingListDao().getAll();

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

        for(int i = 0; i < shoppingLists.size(); i++) {
            shoppingLists.get(i).itemCount = AppDatabase.getAppDatabase(view.getContext()).shoppingListItemDao().getItemCountForShoppingList(shoppingLists.get(i).id);
        }

        shoppingListAdapter.setShoppingLists(shoppingLists);
        shoppingListAdapter.notifyDataSetChanged();
    }


}
