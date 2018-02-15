package com.jurtz.marcel.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    int shoppingListId;
    String shoppingListDescription;
    List<ShoppingListItem> shoppingListItems;
    ShoppingListItemAdapter shoppingListItemAdapter;
    View parentLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        //getSupportActionBar().hide();

        parentLayout = findViewById(R.id.clShoppingList);
        toolbar = findViewById(R.id.shoppingListToolbar);

        Intent intent = getIntent();
        shoppingListId = intent.getIntExtra("shoppinglist_id", -1);
        shoppingListDescription = intent.getStringExtra("shoppinglist_description");
        toolbar.setTitle(shoppingListDescription);

        RecyclerView rvShoppingListItems = findViewById(R.id.rvShoppingListItems);
        rvShoppingListItems.setLayoutManager(new LinearLayoutManager(this));

        shoppingListItems = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().getAllForShoppingList(shoppingListId);
        shoppingListItemAdapter = new ShoppingListItemAdapter(shoppingListItems);
        shoppingListItemAdapter.SetOnItemClickListener(new ShoppingListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                removeEntry(position);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabShoppingListItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNewEntryDialog();
            }
        });

        reloadAdapter();
        rvShoppingListItems.setAdapter(shoppingListItemAdapter);
    }

    private void reloadAdapter() {
        shoppingListItems = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().getAllForShoppingList(shoppingListId);
        shoppingListItemAdapter.setShoppingListItems(shoppingListItems);
        shoppingListItemAdapter.notifyDataSetChanged();
    }

    private void loadNewEntryDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText txtInput = (EditText) dialogView.findViewById(R.id.txtDialogInput);

        dialogBuilder.setTitle(getResources().getString(R.string.dialog_title));
        dialogBuilder.setMessage(getResources().getString(R.string.dialog_message));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String input = txtInput.getText().toString().trim();
                addNewEntry(input);
            }
        });
        dialogBuilder.setNegativeButton(getResources().getString(R.string.dialog_negative_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // pass
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addNewEntry(String description) {
        ShoppingListItem item = new ShoppingListItem();
        item.description = description;
        item.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        item.listId = shoppingListId;
        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().insertItem(item);
        shoppingListItems.add(item);
        reloadAdapter();
    }

    private void removeEntry(int position) {
        final ShoppingListItem item = shoppingListItems.get(position);
        shoppingListItems.remove(item);
        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().deleteItem(item);
        reloadAdapter();

        // Show snackbar to restore entry
        Snackbar.make(parentLayout, getResources().getString(R.string.snackbar_information), Snackbar.LENGTH_LONG).
                setAction(getResources().getString(R.string.snackbar_restore), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shoppingListItems.add(item);
                        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().insertItem(item);
                        reloadAdapter();
                    }

                }).show();

    }

    @Override
    protected void onPause() {
        if(shoppingListItems.size() == 0) {
            AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().deleteListById(shoppingListId);
        }
        super.onPause();
    }
}
