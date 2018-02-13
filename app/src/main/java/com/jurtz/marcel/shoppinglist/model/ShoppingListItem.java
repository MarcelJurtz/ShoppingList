package com.jurtz.marcel.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {

    /*
    public ShoppingListItem() {
        this.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
    }

    public ShoppingListItem(int id, String description, int timestampSeconds) {
        this.id = id;
        this.description = description;
        this.timestampSeconds = timestampSeconds;
    }

*/
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "timestamp_seconds")
    public int timestampSeconds;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestampSeconds(int timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }
    */
}
