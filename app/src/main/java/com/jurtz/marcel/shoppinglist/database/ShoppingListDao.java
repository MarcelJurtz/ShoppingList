package com.jurtz.marcel.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jurtz.marcel.shoppinglist.model.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list")
    List<ShoppingList> getAll();

    @Query("DELETE FROM shopping_list WHERE id = :id")
    void deleteListById(int id);

    @Insert()
    void insertList(ShoppingList shoppingList);

    @Update()
    void updateList(ShoppingList shoppingList);

    @Delete
    void deleteList(ShoppingList shoppingList);
}