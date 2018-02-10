package com.jurtz.marcel.shoppinglist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jurtz.marcel.shoppinglist.model.ShoppingList
import com.jurtz.marcel.shoppinglist.model.ShoppingListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvShoppingLists.layoutManager = LinearLayoutManager(this)

        var list1 = ShoppingList()
        list1.timestamp = (Calendar.getInstance().timeInMillis / 1000).toInt()
        list1.description = "Einkaufsliste"

        var list2 = ShoppingList()
        list2.timestamp = (Calendar.getInstance().timeInMillis / 1000).toInt()
        list2.description = "Klamotten"

        var list3 = ShoppingList()
        list3.timestamp = (Calendar.getInstance().timeInMillis / 1000).toInt()
        list3.description = "Shopping"

        var list = listOf(list1, list2, list3)

        rvShoppingLists.adapter = ShoppingListAdapter(list)


    }
}
