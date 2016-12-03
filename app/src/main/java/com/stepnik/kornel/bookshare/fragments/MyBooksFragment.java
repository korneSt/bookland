package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.adapters.BooksAdapter;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.LoginResponse;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookServiceAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 31.10.2016.
 */

public class MyBooksFragment extends Fragment {
    OnBookSelectedListener mCallback;

    ListView lvBooks;
    BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);
    private BooksAdapter booksAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnBookSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mybooks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView tempTextView = (TextView) view.findViewById(R.id.temp_text_mybooks);
            tempTextView.setText("My books");

            lvBooks = (ListView) view.findViewById(R.id.lv_mybooks);

            getBooks();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void getBooks() {
        LoginResponse user= AppData.loggedUser;
        Call<List<Book>> books = bookServiceAPI.getUserBooks(AppData.loggedUser.getUserId());
        Log.d("user id", String.valueOf(AppData.loggedUser.getUserId()));
        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, retrofit2.Response<List<Book>> response) {

                if (response.isSuccessful()) {
                    setAdapter(response);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("status", String.valueOf(t.getStackTrace()));

            }
        });
    }

    public void setAdapter(final Response<List<Book>> response) {

        booksAdapter = new BooksAdapter(this.getContext(), (ArrayList<Book>) response.body());
        lvBooks.setAdapter(booksAdapter);
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onBookSelected(response.body().get(i), false);

            }
        });
    }
}
