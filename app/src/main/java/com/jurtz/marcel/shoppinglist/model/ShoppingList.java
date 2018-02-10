package com.jurtz.marcel.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName="shopping_list")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="timestamp_seconds")
    private int timestamp;
    @Embedded
    List<ShoppingListItem> items;
}
