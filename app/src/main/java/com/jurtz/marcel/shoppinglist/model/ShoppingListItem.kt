package com.jurtz.marcel.shoppinglist.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "shopping_list_item")
data class ShoppingListItem(
        @ColumnInfo(name = "description")
        var description: String? = null,

        @ColumnInfo(name = "timestamp")
        var timestamp: Int = (Calendar.getInstance().timeInMillis / 1000).toInt()
) {

    @PrimaryKey(autoGenerate = true)
    private val id: Int = 0

}