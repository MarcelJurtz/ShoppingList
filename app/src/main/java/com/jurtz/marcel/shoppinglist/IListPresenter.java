package com.jurtz.marcel.shoppinglist;

import com.jurtz.marcel.shoppinglist.model.ShoppingList;

public interface IListPresenter {
    void onShoppingListClick(int position);
    void onFloatingActionButtonClick();
    void onSortButtonClick();
    void onEntryDialogConfirmation(String input);
    void onResume();
    void onCreate();
}
