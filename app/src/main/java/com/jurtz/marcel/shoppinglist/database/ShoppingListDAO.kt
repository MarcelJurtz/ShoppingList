package com.jurtz.marcel.shoppinglist.database

import android.arch.persistence.room.*
import com.jurtz.marcel.shoppinglist.model.ShoppingList

@Dao
interface ShoppingListDAO {
    @Query("SELECT * FROM shopping_list")
    fun getAll() : List<ShoppingList>

    @Query("SELECT * FROM shopping_list WHERE id = :p0")
    fun findTaskById(id: Long): ShoppingList

    @Insert()
    fun insertTask(shoppingList: ShoppingList)

    @Update()
    fun updateTask(shoppingList: ShoppingList)

    @Delete
    fun deleteTask(shoppingList: ShoppingList)
}