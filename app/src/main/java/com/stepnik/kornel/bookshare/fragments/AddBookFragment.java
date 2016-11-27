package com.stepnik.kornel.bookshare.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;

import static com.stepnik.kornel.bookshare.R.id.imageView;

/**
 * Created by korSt on 31.10.2016.
 */

public class AddBookFragment extends Fragment {

    private BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);
    private EditText etTittle;
    private EditText etAuthor;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView ivBookCover;

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
        ivBookCover = (ImageView) rootView.findViewById(R.id.iv_book_cover);
        Button addBook = (Button) rootView.findViewById(R.id.b_addbook);
        Button takePicture = (Button) rootView.findViewById(R.id.b_takepic);

        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Add book");

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBook();
            }
        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        return rootView;
    }

    private void addNewBook() {
        Call<Book> addBook = bookServiceAPI.addBook(etTittle.getText().toString(), etAuthor.getText().toString(), AppData.loggedUser.getUserId());

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivBookCover.setImageBitmap(photo);
        }
    }
}
