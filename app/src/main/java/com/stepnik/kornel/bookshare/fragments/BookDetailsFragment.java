package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;

import retrofit2.http.POST;

/**
 * Created by korSt on 03.11.2016.
 */

public class BookDetailsFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    public static final String ARG_BOOK = "book";
    int mCurrentPosition = -1;
    Book selectedBook;
    OnBookSelectedListener mCallback;


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
        View rootView = inflater.inflate(R.layout.fragment_book_details, container, false);
        TextView tempTextView = (TextView) rootView.findViewById(R.id.temp_text_mybooks);
        Button rentBook = (Button) rootView.findViewById(R.id.b_rentbook);
        tempTextView.setText("My books");

        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            selectedBook = (Book) savedInstanceState.getSerializable(ARG_BOOK);
        } else {
            selectedBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }

        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Book detals");

        rentBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onRentBookSelected(selectedBook);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_BOOK, selectedBook);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView((Book) args.getSerializable(ARG_BOOK));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
//            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(Book book) {
        TextView article = (TextView) getActivity().findViewById(R.id.desc);
        article.setText(book.getTitle());
//        mCurrentPosition = position;
    }
}
