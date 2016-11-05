package com.stepnik.kornel.bookshare.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.stepnik.kornel.bookshare.models.Book;

import java.util.List;

/**
 * Created by korSt on 31.10.2016.
 */

public class MyBooksListAdapter extends ArrayAdapter<Book>{

    public MyBooksListAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

}
