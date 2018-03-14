package com.jurtz.marcel.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;

@Database(entities = {ShoppingList.class, ShoppingListItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ShoppingListDao shoppingListDao();
    public abstract ShoppingListItemDao shoppingListItemDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "shoppinglist-db")
                            // TODO only run queries on separate threads. See PersistenceBasicSample for an example.
                            //.allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}