package com.jurtz.marcel.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jurtz.marcel.shoppinglist.database.AppDatabase;
import com.jurtz.marcel.shoppinglist.model.ShoppingList;
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtDescription = (TextView)findViewById(R.id.txtAddNewShoppingList);

        RecyclerView rvShoppingLists = findViewById(R.id.rvShoppingLists);
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(this));

        List<ShoppingList> lists = AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().getAll();
        rvShoppingLists.setAdapter(new ShoppingListAdapter(lists));

        Button cmdAddNewShoppingList = findViewById(R.id.cmdAddNewShoppingList);
        cmdAddNewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = txtDescription.getText().toString().trim();
                if(description != null && description.length() > 0) {
                    ShoppingList list = new ShoppingList();
                    list.setDescription(txtDescription.getText().toString());
                    AppDatabase.getAppDatabase(getApplicationContext()).shoppingListDao().insertList(list);
                }
            }
        });

    }
}
