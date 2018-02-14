package com.jurtz.marcel.shoppinglist.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jurtz.marcel.shoppinglist.R;

import java.util.List;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListItemViewHolder> {

    private List<ShoppingListItem> shoppingListItems;
    OnItemClickListener mItemClickListener;

    public ShoppingListItemAdapter(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    @Override
    public ShoppingListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cellForRow = layoutInflater.inflate(R.layout.shopping_list_item_row, parent, false);
        return new ShoppingListItemViewHolder(cellForRow);
    }

    @Override
    public void onBindViewHolder(ShoppingListItemViewHolder holder, int position) {
        if(holder != null) {
            holder.bindShoppingListItem(shoppingListItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(shoppingListItems == null) {
            return 0;
        } else {
            return shoppingListItems.size();
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView header;

        public ShoppingListItemViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.lblShoppingListItemRowItemHeader);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }

        public void bindShoppingListItem(ShoppingListItem shoppingListItem) {
            if(shoppingListItem != null) {
                header.setText(shoppingListItem.description);
            }
        }
    }
}
