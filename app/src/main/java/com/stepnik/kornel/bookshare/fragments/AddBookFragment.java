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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.services.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by korSt on 31.10.2016.
 */

public class AddBookFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.101:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    BookService bookService = retrofit.create(BookService.class);
    EditText etTittle;
    EditText etAuthor;
    EditText etOwnerId;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTittle = (EditText) rootView.findViewById(R.id.te_title);
        etAuthor = (EditText) rootView.findViewById(R.id.te_author);
        etOwnerId = (EditText) rootView.findViewById(R.id.te_ownerId);
        Button addBook = (Button) rootView.findViewById(R.id.b_addbook);

        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Add book");

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBook();
            }
        });

        return rootView;
    }

    private void addNewBook() {
        Call<Book> addBook = bookService.addBook(etTittle.getText().toString(), etAuthor.getText().toString(), 1L);

        addBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, retrofit2.Response<Book> response) {
                Log.d("response", response.toString());
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("response F", t.getMessage());

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
