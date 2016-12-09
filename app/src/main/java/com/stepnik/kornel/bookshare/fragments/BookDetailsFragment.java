package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.services.BookService;

import retrofit2.http.POST;

/**
 * Created by korSt on 03.11.2016.
 */

public class BookDetailsFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    public static final String ARG_BOOK = "book";
    public static final String ARG_SHOW_RENT_BUTTON = "showRentButton";
    int mCurrentPosition = -1;
    Book selectedBook;
    OnBookSelectedListener mCallback;
    TextView title;
    TextView author;
    RatingBar condition;
    ImageView bookCover;



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
        title = (TextView) rootView.findViewById(R.id.tv_author_detail);
        author = (TextView) rootView.findViewById(R.id.desc);
        condition = (RatingBar) rootView.findViewById(R.id.rb_condition_book);
        Button rentBook = (Button) rootView.findViewById(R.id.b_rentbook);
        bookCover = (ImageView) rootView.findViewById(R.id.imageView2);
        Button deleteBook = (Button) rootView.findViewById(R.id.b_delete_book);

        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            selectedBook = (Book) savedInstanceState.getSerializable(ARG_BOOK);
        } else {
            selectedBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }


        if (!getArguments().getBoolean(ARG_SHOW_RENT_BUTTON)) {
            rentBook.setVisibility(View.GONE);
        }
        if (getArguments().getBoolean(ARG_SHOW_RENT_BUTTON)) {
            deleteBook.setVisibility(View.GONE);
        }

        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onDeleteBookSelected(selectedBook.getId());
//                new BookService().deleteBook(selectedBook.getId());
            }
        });
        rentBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onRentBookSelected(selectedBook);
            }
        });

        Picasso.with(getContext()).load(selectedBook.getImagePath()).into(bookCover);

        updateTextViews(selectedBook);

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
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

        updateTextViews(selectedBook);

    }

    public void updateTextViews(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        condition.setRating(book.getCondition());
        if (book.getImagePath() != null) {
            Picasso.with(getContext()).load(book.getImagePath()).into(bookCover);
        } else {
            bookCover.setImageResource(R.drawable.image_holder_big);
        }
    }
}
