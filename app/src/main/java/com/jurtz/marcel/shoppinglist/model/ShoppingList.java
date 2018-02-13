package com.jurtz.marcel.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    /*
    public ShoppingList() {
        timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        items = new ArrayList<>();
    }

    public ShoppingList(int id, String description, int timestampSeconds, List<ShoppingListItem> items) {
        this.id = id;
        this.description = description;
        this.timestampSeconds = timestampSeconds;
        this.items = items;
    }
    */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "timestamp_seconds")
    public int timestampSeconds;

    @Embedded
    public List<ShoppingListItem> items;

    /*
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getTimestampSeconds() {
        return timestampSeconds;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestampSeconds(int timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }

    public void addItem(ShoppingListItem item) {
        this.items.add(item);
    }

    public void removeItem(ShoppingListItem item) {
        this.items.remove(item);
    }
    */
}
