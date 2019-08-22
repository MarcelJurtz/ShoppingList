package com.jurtz.marcel.sist.contract;

import com.jurtz.marcel.sist.model.*;

import java.util.List;


public interface IListPresenter {
    void onListClick(int position);
    void onListLongClick(int position);
    void onFabClick();
    void onSortClick();
    void onAddDialogConfirm(String input);
    void onEditDialogConfirm(ShoppingList item, String input);
    void onResume();

    void refresh(List<ShoppingList> lists);
}
