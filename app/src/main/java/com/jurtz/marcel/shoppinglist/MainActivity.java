package com.jurtz.marcel.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ShoppingList> shoppingLists;
    ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtDescription = (TextView) findViewById(R.id.txtAddNewShoppingList);

        RecyclerView rvShoppingLists = findViewById(R.id.rvShoppingLists);
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(this));

        shoppingLists = new ArrayList<>();
        shoppingListAdapter = new ShoppingListAdapter(shoppingLists);
        shoppingListAdapter.SetOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                intent.putExtra("shoppinglist_id", shoppingLists.get(position).id);
                startActivity(intent);

            }
        });

        reloadAdapter();
        rvShoppingLists.setAdapter(shoppingListAdapter);

        Button cmdAddNewShoppingList = findViewById(R.id.cmdAddNewShoppingList);
        cmdAddNewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = txtDescription.getText().toString().trim();
                if (description != null && description.length() > 0) {
                    ShoppingList list = new ShoppingList();
                    list.description = txtDescription.getText().toString();
                    AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().insertList(list);
                    txtDescription.setText("");
                    reloadAdapter();
                }
            }
        });
    }

    private void reloadAdapter() {
        shoppingLists = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().getAll();
        for(int i = 0; i < shoppingLists.size(); i++) {
            shoppingLists.get(i).itemCount = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListItemDao().getItemCountForShoppingList(shoppingLists.get(i).id);
        }
        shoppingListAdapter.setShoppingLists(shoppingLists);
        shoppingListAdapter.notifyDataSetChanged();
    }


}
