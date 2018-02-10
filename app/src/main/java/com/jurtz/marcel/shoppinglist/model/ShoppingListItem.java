package com.jurtz.marcel.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="timestamp_seconds")
    private int timestamp;
}