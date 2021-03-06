package com.jurtz.marcel.sist.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jurtz.marcel.shoppinglist.R;
import com.jurtz.marcel.sist.contract.IOnItemClickListener;
import com.jurtz.marcel.sist.contract.IOnItemLongClickListener;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private List<ShoppingList> shoppingLists;
    private IOnItemClickListener mItemClickListener;
    private IOnItemLongClickListener mItemLongClickListener;

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

    public void setOnItemClickListener(final IOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final IOnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private TextView header;
        private TextView subHeader;
        private View view;

        public ShoppingListViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.lblShoppingListRowItemHeader);
            subHeader = v.findViewById(R.id.lblShoppingListRowItemSubheader);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    mItemLongClickListener.OnItemLongClick(v, getAdapterPosition());
                    return true;
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void bindShoppingList(ShoppingList shoppingList) {
            if(shoppingList != null) {

                header.setText(shoppingList.description);

                String suffix = shoppingList.itemCount == 1 ?
                        header.getContext().getResources().getString(R.string.rv_shopping_list_items_suffix_single) :
                        header.getContext().getResources().getString(R.string.rv_shopping_list_items_suffix_multiple);

                subHeader.setText(shoppingList.itemCount + " " + suffix);
            }
        }
    }
}
