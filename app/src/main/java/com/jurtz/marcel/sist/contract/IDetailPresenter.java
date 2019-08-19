package com.jurtz.marcel.sist.contract;

import com.jurtz.marcel.sist.model.*;

public interface IDetailPresenter {
    void onShoppingListItemClick(int position);
    void onDetailItemLongClick(int position);
    void onFloatingActionButtonClick();
    void onSortButtonClick();
    void onEntryDialogConfirmation(String input);
    void onRestoreSnackbarClick(ShoppingListItem item);
    void onPause();
    void onResume();
}
