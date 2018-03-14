package com.jurtz.marcel.shoppinglist;

import android.app.AlertDialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItem;
import com.jurtz.marcel.shoppinglist.model.ShoppingListItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity implements IDetailView {

    int shoppingListId;
    String shoppingListDescription;
    List<ShoppingListItem> shoppingListItems;
    ShoppingListItemAdapter shoppingListItemAdapter;
    View parentLayout;
    Toolbar toolbar;
    RecyclerView rvShoppingListItems;

    private ImageButton cmdSort;
    IDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        //getSupportActionBar().hide();

        parentLayout = findViewById(R.id.clShoppingList);
        toolbar = findViewById(R.id.shoppingListToolbar);

        rvShoppingListItems = findViewById(R.id.rvShoppingListItems);
        rvShoppingListItems.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        shoppingListId = intent.getIntExtra("shoppinglist_id", -1);
        shoppingListDescription = intent.getStringExtra("shoppinglist_description");
        toolbar.setTitle(shoppingListDescription);

        presenter = new ShoppingListPresenter(this, shoppingListId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabShoppingListItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFloatingActionButtonClick();
            }
        });

        cmdSort = (ImageButton)findViewById(R.id.cmdSortItems);
        cmdSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSortButtonClick();
            }
        });

        rvShoppingListItems.setAdapter(shoppingListItemAdapter);

        presenter.onResume();
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void initAdapter(ShoppingListItemAdapter adapter) {
        rvShoppingListItems.setAdapter(adapter);
        adapter.SetOnItemClickListener(new ShoppingListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                presenter.onShoppingListItemClick(position);
            }
        });
    }

    @Override
    public void loadNewEntryDialog() {

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
                presenter.onEntryDialogConfirmation(input);
            }
        });
        dialogBuilder.setNegativeButton(getResources().getString(R.string.dialog_negative_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // pass
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        b.show();
    }

    @Override
    public void loadRestoreSnackbar(final ShoppingListItem item) {
        Snackbar.make(parentLayout, getResources().getString(R.string.snackbar_information), Snackbar.LENGTH_LONG).
                setAction(getResources().getString(R.string.snackbar_restore), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        presenter.onRestoreSnackbarClick(item);
                    }

                }).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
