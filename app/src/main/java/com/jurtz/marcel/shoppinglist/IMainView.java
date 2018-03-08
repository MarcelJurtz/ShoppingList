package com.jurtz.marcel.shoppinglist;

import android.content.Context;
import android.content.Intent;

import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;

import java.util.List;

public interface IMainView {
    Context getContext();
    void loadNewEntryDialog();
    void loadIntent(Intent intent);
    void initAdapter(ShoppingListAdapter adapter);
}
