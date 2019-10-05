package com.jurtz.marcel.sist.contract;

import android.content.Context;

import com.jurtz.marcel.sist.model.*;

public interface IDetailView {
    Context getContext();
    void loadNewEntryDialog();
    void initAdapter(ShoppingListItemAdapter adapter);
    void loadRestoreSnackbar(ShoppingListItem item);
}
