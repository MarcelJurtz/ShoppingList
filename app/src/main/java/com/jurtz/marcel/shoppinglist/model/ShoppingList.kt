package com.jurtz.marcel.shoppinglist.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.Calendar

@Entity(tableName = "shopping_list")
data class ShoppingList(
        @ColumnInfo(name = "description")
        var description: String? = null,

        @ColumnInfo(name = "timestamp")
        var timestamp: Int = (Calendar.getInstance().timeInMillis / 1000).toInt(),

        @Embedded
        private var items : MutableList<ShoppingListItem> = arrayListOf()
) {

    @PrimaryKey(autoGenerate = true)
    private val id: Int = 0

    fun getItemCount() : Int {
        return items.size
    }

    fun addItem(item : ShoppingListItem) {
        items.add(item)
    }

    fun removeItem(item: ShoppingListItem) {
        items.remove(item)
    }
}
