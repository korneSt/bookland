package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.adapters.BooksAdapter;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.BookEvent;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.User;
import com.stepnik.kornel.bookshare.services.BookService;
import com.stepnik.kornel.bookshare.services.UserService;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Iza on 17/11/2016.
 */

public class UserFragment extends Fragment {

    public static final String ARG_USER = "user";
    private User selectetUser;
    private ListView lvUserRentBooks;
    private BookService bookService;
    private BooksAdapter booksAdapter;
    private OnBookSelectedListener bookCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            bookCallback = (OnBookSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        final MainActivity mainActivity = (MainActivity) getContext();
        TextView username = (TextView) rootView.findViewById(R.id.user_username);
        TextView userReputation = (TextView) rootView.findViewById(R.id.user_reputation);
        ProgressBar userLevel = (ProgressBar) rootView.findViewById(R.id.user_progress);

        lvUserRentBooks = (ListView) rootView.findViewById(R.id.lv_user_rent_books);

        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            selectetUser = (User) savedInstanceState.getSerializable(ARG_USER);
        } else {
            selectetUser = (User) getArguments().getSerializable(ARG_USER);
        }

        mainActivity.setTitle(selectetUser.getUsername());
        username.setText(selectetUser.getUsername());
        userReputation.setText(String.valueOf(selectetUser.getRatePos()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        bookService = new BookService();
        bookService.getUserRentBooks(selectetUser.getId());
    }

    @Subscribe
    public void onBooksEvent(final BookEvent event) {
        booksAdapter = new BooksAdapter(this.getContext(), (ArrayList<Book>) event.results);
        lvUserRentBooks.setAdapter(booksAdapter);

        lvUserRentBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bookCallback.onBookSelected(event.results.get(i), true);
            }
        });
    }

}
