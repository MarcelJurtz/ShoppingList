package com.jurtz.marcel.shoppinglist.model

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jurtz.marcel.shoppinglist.R
import kotlinx.android.synthetic.main.shopping_list_row.view.*

class ShoppingListAdapter(var shoppingLists: List<ShoppingList?>) : RecyclerView.Adapter<ShoppingListViewHolder>() {

    override fun getItemCount(): Int {
        return shoppingLists.size ?: 0
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder?, position: Int) {
        holder?.bindShoppingList(shoppingList = shoppingLists.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShoppingListViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.shopping_list_row, parent, false)
        return ShoppingListViewHolder(cellForRow)
    }
}

class ShoppingListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bindShoppingList(shoppingList: ShoppingList?) {
        view.lblShoppingListRowItemHeader.setText(shoppingList?.description)


        var suffix = ""
        var count = shoppingList?.getItemCount() ?: 0
        if(count == 1) {
            suffix = view.context.resources.getString(R.string.rv_shopping_list_items_suffix_single)
        } else {
            suffix = view.context.resources.getString(R.string.rv_shopping_list_items_suffix_multiple)
        }


        view.lblShoppingListRowItemSubheader.setText(count.toString() + " " + suffix)
    }
}