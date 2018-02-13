package com.jurtz.marcel.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    /*
    public ShoppingList(int id, String description, int timestampSeconds, List<ShoppingListItem> items) {
        this.id = id;
        this.description = description;
        this.timestampSeconds = timestampSeconds;
        this.items = items;
    }
    */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "timestamp_seconds")
    public int timestampSeconds;

    //@Relation(parentColumn = "id", entityColumn = "listId")
    @Embedded
    public List<ShoppingListItem> items;
}
