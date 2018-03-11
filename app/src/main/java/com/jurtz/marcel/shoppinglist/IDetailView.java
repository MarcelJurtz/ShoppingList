package com.jurtz.marcel.shoppinglist;

import android.content.Context;

import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItemAdapter;

public interface IDetailView {
    Context getContext();
    void loadNewEntryDialog();
    void initAdapter(ShoppingListItemAdapter adapter);
    void loadRestoreSnackbar(ShoppingListItem item);
}
