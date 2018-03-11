package com.jurtz.marcel.shoppinglist;

import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;

public interface IDetailPresenter {
    void onShoppingListItemClick(int position);
    void onFloatingActionButtonClick();
    void onSortButtonClick();
    void onEntryDialogConfirmation(String input);
    void onRestoreSnackbarClick(ShoppingListItem item);
    void onCreate();
    void onPause();
    void onResume();
}
