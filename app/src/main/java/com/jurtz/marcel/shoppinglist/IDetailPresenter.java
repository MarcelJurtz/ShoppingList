package com.jurtz.marcel.shoppinglist;

public interface IDetailPresenter {
    void onShoppingListItemClick(int position);
    void onFloatingActionButtonClick();
    void onSortButtonClick(); // TODO
    void onEntryDialogConfirmation(String input);
    void onResume();
    void onCreate();
}
