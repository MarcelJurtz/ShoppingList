package com.jurtz.marcel.sist.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jurtz.marcel.shoppinglist.R;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private List<ShoppingList> shoppingLists;
    OnItemClickListener mItemClickListener;
    AdapterView.OnItemLongClickListener mItemLongClickListener;

    public ShoppingListAdapter(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final AdapterView.OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView header;
        private TextView subHeader;
        private View view;

        public ShoppingListViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.lblShoppingListRowItemHeader);
            subHeader = v.findViewById(R.id.lblShoppingListRowItemSubheader);
            v.setOnClickListener(this);

            ((CardView)v).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {



                    return false; // TODO What to return?
                }
            });
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }

        public void onLongClick(View v) {

            //mItemLongClickListener.onItemLongClick(null, v, getAdapterPosition());
            getAdapterPosition(); }

       /* @Override
        public void onItemLongClick(RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> adp, View v, int pos, long id) {

        }*/





        public void bindShoppingList(ShoppingList shoppingList) {
            if(shoppingList != null) {

                header.setText(shoppingList.description);

                String suffix = "";
                int count = shoppingList.itemCount;

                if (count == 1) {
                    suffix = header.getContext().getResources().getString(R.string.rv_shopping_list_items_suffix_single);
                } else {
                    suffix = header.getContext().getResources().getString(R.string.rv_shopping_list_items_suffix_multiple);
                }

                subHeader.setText(count + " " + suffix);
            }
        }
    }
}
