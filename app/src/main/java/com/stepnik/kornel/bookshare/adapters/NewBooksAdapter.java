package com.stepnik.kornel.bookshare.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;

import java.util.ArrayList;

/**
 * Created by korSt on 31.10.2016.
 */

public class NewBooksAdapter extends RecyclerView.Adapter<NewBooksAdapter.ViewHolder>{
    private ArrayList<Book> newBookList;
    private Context context;

    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public NewBooksAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.newBookList = bookList;
    }
    public Context getContext() {
        return context;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView tvTittle;
        TextView tvAuthor;
        ImageView cover;

        ViewHolder(final View itemView) {
            super(itemView);
            tvTittle = (TextView) itemView.findViewById(R.id.tv_title_new);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author_new);
            cover = (ImageView) itemView.findViewById(R.id.iv_book_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewBooksAdapter(ArrayList<Book> myDataset) {
        newBookList = myDataset;
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
        holder.tvTittle.setText(newBookList.get(position).getTitle());
        holder.tvAuthor.setText(newBookList.get(position).getAuthor());
        Picasso.with(getContext()).load("https://unsplash.it/50/70/?image=" + position).into(holder.cover);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newBookList.size();
    }

    public void setNewBooksList(ArrayList<Book> bookList) {
        this.newBookList = bookList;
        notifyItemRangeChanged(0, bookList.size());
    }
}
