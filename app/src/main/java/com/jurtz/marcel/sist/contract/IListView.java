package com.jurtz.marcel.sist.contract;

import android.content.Context;
import android.content.Intent;

import com.jurtz.marcel.sist.model.ShoppingList;
import com.jurtz.marcel.sist.model.ShoppingListAdapter;

public interface IListView {
    Context getContext();
    void loadNewListDialog();
    void loadEditListDialog(ShoppingList item);
    void loadIntent(Intent intent);
    void initAdapter(ShoppingListAdapter adapter);
}
