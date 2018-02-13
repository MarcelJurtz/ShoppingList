package com.jurtz.marcel.shoppinglist.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jurtz.marcel.shoppinglist.R;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private List<ShoppingList> shoppingLists;
    public ShoppingListAdapter(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cellForRow = layoutInflater.inflate(R.layout.shopping_list_row, parent, false);
        return new ShoppingListViewHolder(cellForRow);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        if(holder != null) {
            holder.bindShoppingList(shoppingLists.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(shoppingLists == null) {
            return 0;
        } else {
            return shoppingLists.size();
        }
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private TextView header;
        private TextView subHeader;
        private View view;

        public ShoppingListViewHolder(View v) {
            super(v);
            this.view = view;
            header = v.findViewById(R.id.lblShoppingListRowItemHeader);
            subHeader = v.findViewById(R.id.lblShoppingListRowItemSubheader);
        }

        public void bindShoppingList(ShoppingList shoppingList) {
            if(shoppingList != null) {

                header.setText(shoppingList.description);

                String suffix = "";
                int count = shoppingList.items.size();
                if (count == 1) {
                    suffix = view.getContext().getResources().getString(R.string.rv_shopping_list_items_suffix_single);
                } else {
                    suffix = view.getContext().getResources().getString(R.string.rv_shopping_list_items_suffix_multiple);
                }


                subHeader.setText(count + " " + suffix);
            }
        }
    }
}
