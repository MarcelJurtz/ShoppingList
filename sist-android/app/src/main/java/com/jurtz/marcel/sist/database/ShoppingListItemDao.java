package com.jurtz.marcel.sist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jurtz.marcel.sist.model.ShoppingListItem;

import java.util.List;

@Dao
public interface ShoppingListItemDao {
    @Query("SELECT * FROM shopping_list_item WHERE list_id = :shoppingListId")
    List<ShoppingListItem> getAllForShoppingList(int shoppingListId);

    @Query("SELECT COUNT(*) FROM shopping_list_item WHERE list_id = :shoppingListId")
    int getItemCountForShoppingList(int shoppingListId);

    @Insert()
    void insertItem(ShoppingListItem shoppingListItem);

    @Update()
    void updateItem(ShoppingListItem shoppingListItem);

    @Delete
    void deleteItem(ShoppingListItem shoppingListItem);
}
