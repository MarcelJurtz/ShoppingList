package com.jurtz.marcel.sist.contract;

import android.content.Context;
import android.content.Intent;

import com.jurtz.marcel.sist.model.ShoppingListAdapter;
import com.jurtz.marcel.sist.model.ShoppingListItem;

public interface IListView {
    Context getContext();
    void loadNewListDialog();
    void loadEditListDialog(ShoppingListItem item);
    void loadIntent(Intent intent);
    void initAdapter(ShoppingListAdapter adapter);
}
