package com.stepnik.kornel.bookshare.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;

/**
 * Created by korSt on 31.10.2016.
 */

public class NewBooksAdapter extends RecyclerView.Adapter<NewBooksAdapter.ViewHolder>{
    private Book[] mDataset;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTittle;
        public TextView tvAuthor;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTittle = (TextView) itemView.findViewById(R.id.tv_title_new);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author_new);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewBooksAdapter(Book[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewBooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_book, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvTittle.setText(mDataset[position].getTitle());
        holder.tvAuthor.setText(mDataset[position].getAuthor());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
