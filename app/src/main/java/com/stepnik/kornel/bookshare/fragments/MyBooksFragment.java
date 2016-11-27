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
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.LoginResponse;
import com.stepnik.kornel.bookshare.models.User;
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
    ArrayList<HashMap<String, String>> bookList;
    ArrayList<Book> bookListData;
    BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
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

            bookList = new ArrayList<>();
            bookListData = new ArrayList<>();
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
                bookList = new ArrayList<>();
                Log.d("status", String.valueOf(response.code()));
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

    public void setAdapter(Response<List<Book>> response) {
        for (Book book : response.body()) {
            bookListData.add(book);
            HashMap<String, String> bookTemp = new HashMap<>();
            bookTemp.put("title", book.getTitle());
            bookTemp.put("author", book.getAuthor());
            bookList.add(bookTemp);
        }

        ListAdapter adapter = new SimpleAdapter(
                getContext(), bookList,
                R.layout.list_item, new String[]{"title", "author"},
                new int[]{R.id.tv_title, R.id.tv_author}
        );
        lvBooks.setAdapter(adapter);
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onBookSelected(bookListData.get(i), false);
            }
        });
    }
}
