package com.jurtz.marcel.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {

    /*
    public ShoppingListItem(int id, String description, int timestampSeconds) {
        this.id = id;
        this.description = description;
        this.timestampSeconds = timestampSeconds;
    }
    */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "listId")
    public int listId;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "timestamp_seconds")
    public int timestampSeconds;
}
