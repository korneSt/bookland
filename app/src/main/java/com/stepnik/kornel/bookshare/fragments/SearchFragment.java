package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by korSt on 04.11.2016.
 */
public class SearchFragment extends Fragment {
    OnBookSelectedListener mCallback;
    ListView searchResults;
    private ArrayList<Book> bookData;
    private ArrayList<HashMap<String, String>> bookList;
    ListAdapter adapter;


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

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_search,container,false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Search");
        searchResults = (ListView) rootView.findViewById(R.id.lv_searchResults);

        final EditText searchView = (EditText) rootView.findViewById(R.id.sv_book);
        Button searchButton = (Button) rootView.findViewById(R.id.b_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BookService().searchBook(AppData.loggedUser.getUserId(), searchView.getText().toString());
            }
        });

        return rootView;
    }

    @Subscribe
    public void onSearchResult(NewBooksEvent event) {
        bookList = new ArrayList<>();
        bookData = new ArrayList<>();

        for (Book b : event.result.body()) {
            bookData.add(b);
            HashMap<String, String> tempBook = new HashMap<>();
            tempBook.put("title", String.valueOf(b.getTitle()));
            tempBook.put("author", String.valueOf(b.getAuthor()));
            bookList.add(tempBook);
        }
        adapter = new SimpleAdapter(
                getContext(), bookList,
                R.layout.list_item, new String[]{"title", "author"},
                new int[]{R.id.tv_item_primary, R.id.tv_item_secondary}
        );
        searchResults.setAdapter(adapter);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onBookSelected(bookData.get(i), true);
            }
        });
    }
}