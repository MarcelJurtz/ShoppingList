package com.jurtz.marcel.sist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.util.List;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "timestamp_seconds")
    public int timestampSeconds;

    @Ignore
    public List<ShoppingListItem> items;

    @ColumnInfo(name="item_count")
    public int itemCount;
}
