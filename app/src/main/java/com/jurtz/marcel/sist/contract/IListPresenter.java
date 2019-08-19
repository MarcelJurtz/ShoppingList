package com.jurtz.marcel.sist.contract;

import com.jurtz.marcel.sist.model.*;


public interface IListPresenter {
    void onListClick(int position);
    void onFabClick();
    void onSortClick();
    void onAddDialogConfirm(String input);
    void onEditDialogConfirm(ShoppingListItem item);
    void onResume();
}
