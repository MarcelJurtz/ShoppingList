package com.jurtz.marcel.shoppinglist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.jurtz.marcel.shoppinglist.model.ShoppingList

@Database(entities = arrayOf(ShoppingList::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
}