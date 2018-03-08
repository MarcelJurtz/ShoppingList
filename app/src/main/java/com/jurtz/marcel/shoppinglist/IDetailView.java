package com.jurtz.marcel.shoppinglist;

import android.content.Context;
import android.content.Intent;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItemAdapter;

public interface IDetailView {
    Context getContext();
    void loadNewEntryDialog();
    void loadPreviousActivity(Intent intent);
    void initAdapter(ShoppingListItemAdapter adapter);
}
