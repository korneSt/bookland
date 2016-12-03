package com.stepnik.kornel.bookshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;

import java.util.ArrayList;

/**
 * Created by korSt on 03.12.2016.
 */

public class BooksAdapter extends ArrayAdapter<Book> {

    public BooksAdapter(Context context, int resource) {
        super(context, resource);
    }
    public BooksAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvAuthor = (TextView) convertView.findViewById(R.id.tv_item_secondary);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_item_primary);

        tvAuthor.setText(book.getAuthor());
        tvTitle.setText(book.getTitle());

        return convertView;
    }
}
