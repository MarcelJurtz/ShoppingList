package com.jurtz.marcel.shoppinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;

public class MainActivity extends AppCompatActivity implements IMainView {

    ImageButton cmdSort;
    IMainPresenter presenter;
    RecyclerView rvShoppingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        presenter = new MainPresenter(this);

        rvShoppingLists = findViewById(R.id.rvShoppingLists);
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabShoppingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNewEntryDialog();
            }
        });

        cmdSort = (ImageButton)findViewById(R.id.cmdSortLists);
        cmdSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSortButtonClick();
            }
        });

        presenter.onResume();

    }

    @Override
    public void loadIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void initAdapter(ShoppingListAdapter adapter) {
        rvShoppingLists.setAdapter(adapter);
        adapter.setOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                presenter.onShoppingListClick(position);
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
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
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }
}
