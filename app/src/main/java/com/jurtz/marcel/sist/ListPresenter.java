package com.jurtz.marcel.sist;

import android.content.Intent;
import android.os.AsyncTask;

import com.jurtz.marcel.sist.contract.IListPresenter;
import com.jurtz.marcel.sist.contract.IListView;
import com.jurtz.marcel.sist.database.AppDatabase;
import com.jurtz.marcel.sist.database.ShoppingListDao;
import com.jurtz.marcel.sist.model.ShoppingList;
import com.jurtz.marcel.sist.model.ShoppingListAdapter;
import com.jurtz.marcel.sist.task.*;

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
        view.loadNewListDialog();
    }

    @Override
    public void onSortClick() {
        currentlySortedByTimeStamp = !currentlySortedByTimeStamp;
        refresh(shoppingLists);
    }

    @Override
    public void onAddDialogConfirm(String input) {
        ShoppingList list = new ShoppingList();
        list.description = input;
        list.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        list.itemCount = 0;

        new AddListTask(this, shoppingListDao, list).execute();
    }

    @Override
    public void onEditDialogConfirm(ShoppingList list, String input) { // TODO Implementation
        //ShoppingList list = new ShoppingList();
        //list.description = input;
        //list.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        //list.itemCount = 0;

        //new AddListTask(shoppingListDao, list).execute();

        if(input.isEmpty())
            return;

        list.description = input;
        new RenameListTask(this, shoppingListDao, list).execute();
    }

    @Override
    public void onResume() {
        new GetAllListsTask(this, shoppingListDao).execute();
    }

    public void refresh(List<ShoppingList> lists) {

        shoppingLists = lists;

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
}
