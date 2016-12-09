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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.stepnik.kornel.bookshare.LoginActivity;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.OcrCaptureActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookServiceAPI;
import com.stepnik.kornel.bookshare.services.GoodreadsService;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by korSt on 31.10.2016.
 */

public class AddBookFragment extends Fragment {

    private BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);
    private EditText etTittle;
    private EditText etAuthor;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView ivBookCover;
    private RatingBar ratingBar;
    private EditText etIsbn;

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private ListView lvOcrResults;

    private static final int RC_OCR_CAPTURE = 9003;

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
        ratingBar = (RatingBar) rootView.findViewById(R.id.rb_condition);
        etIsbn = (EditText) rootView.findViewById(R.id.et_isbn);

        autoFocus = (CompoundButton) rootView.findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) rootView.findViewById(R.id.use_flash);
        lvOcrResults = (ListView) rootView.findViewById(R.id.lv_ocr_results);

        Button addBook = (Button) rootView.findViewById(R.id.b_addbook);

        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Add book");

        rootView.findViewById(R.id.read_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
                intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

                startActivityForResult(intent, RC_OCR_CAPTURE);
            }
        });

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addNewBook();
//                new GoodreadsService().searchBooks(etTittle.getText().toString());
                Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void addNewBook() {
        Call<Book> addBook = bookServiceAPI.addBook(etTittle.getText().toString(), etAuthor.getText().toString(),
                AppData.loggedUser.getUserId(),
                Integer.parseInt(etIsbn.getText().toString()),
                "https://s.gr-assets.com/assets/nophoto/book/50x75-a91bf249278a81aabab721ef782c4a74.png",
                ratingBar.getNumStars());

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    ArrayList<String> textArray = data.getStringArrayListExtra(OcrCaptureActivity.TextArray);
                    final ArrayAdapter<String> aa;
                    aa = new ArrayAdapter<>(  getContext(),
                            android.R.layout.simple_list_item_1,
                            textArray
                    );
                    lvOcrResults.setAdapter(aa);
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
