package com.jurtz.marcel.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ShoppingList> shoppingLists;
    ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        RecyclerView rvShoppingLists = findViewById(R.id.rvShoppingLists);
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(this));

        shoppingLists = new ArrayList<>();
        shoppingListAdapter = new ShoppingListAdapter(shoppingLists);
        shoppingListAdapter.setOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                intent.putExtra("shoppinglist_id", shoppingLists.get(position).id);
                startActivity(intent);
            }
        });

        shoppingListAdapter.setOnItemLongClickListener(new ShoppingListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                deleteShoppingList(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabShoppingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNewEntryDialog();
            }
        });

        reloadAdapter();
        rvShoppingLists.setAdapter(shoppingListAdapter);
    }

    private void reloadAdapter() {
        shoppingLists = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().getAll();
        for(int i = 0; i < shoppingLists.size(); i++) {
            shoppingLists.get(i).itemCount = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().getItemCountForShoppingList(shoppingLists.get(i).id);
        }
        shoppingListAdapter.setShoppingLists(shoppingLists);
        shoppingListAdapter.notifyDataSetChanged();
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
        ShoppingList list = new ShoppingList();
        list.description = description;
        list.timestampSeconds = (int)(Calendar.getInstance().getTimeInMillis() / 1000);
        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().insertList(list);
        shoppingLists.add(list);
        reloadAdapter();
    }

    private void deleteShoppingList(int position) {
        ShoppingList list = shoppingLists.get(position);
        shoppingLists.remove(list);
        AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().deleteList(list);
        reloadAdapter();
        // Snackbar
    }


}
