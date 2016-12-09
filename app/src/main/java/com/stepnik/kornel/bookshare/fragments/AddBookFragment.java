package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.OcrCaptureActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.Utilities;
import com.stepnik.kornel.bookshare.adapters.GoodreadsAdapter;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.SearchResponse;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookServiceAPI;
import com.stepnik.kornel.bookshare.services.GoodreadsService;

import java.util.ArrayList;
import java.util.List;

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
    private RequestQueue requestQueue;


    //    FloatingActionButton floatingActionButton = ((MainActivity) getActivity()).getFloatingActionButton();
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private ListView lvOcrResults;
    private GoodreadsAdapter goodreadsAdapter;
    String selectedBookCover = "";

    private static final int RC_OCR_CAPTURE = 9003;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_book, container, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());


        etTittle = (EditText) rootView.findViewById(R.id.te_title);
        etAuthor = (EditText) rootView.findViewById(R.id.te_author);
        ivBookCover = (ImageView) rootView.findViewById(R.id.iv_book_cover);
        ratingBar = (RatingBar) rootView.findViewById(R.id.rb_condition);
        etIsbn = (EditText) rootView.findViewById(R.id.et_isbn);

        autoFocus = (CompoundButton) rootView.findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) rootView.findViewById(R.id.use_flash);
        lvOcrResults = (ListView) rootView.findViewById(R.id.lv_ocr_results);

        Button addBook = (Button) rootView.findViewById(R.id.b_addbook);
        Button searchBooks = (Button) rootView.findViewById(R.id.b_search_gr);
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
        searchBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGoodreadsSearch(etTittle.getText().toString());
            }
        });
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBook();
//                new GoodreadsService().searchBooks("Diuna");
//                getGoodreadsSearch(etTittle.getText().toString());

            }
        });

//        if (floatingActionButton != null) {
//            floatingActionButton.hide();
//        }

//        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
//        p.setAnchorId(View.NO_ID);
//        floatingActionButton.setLayoutParams(p);
//        floatingActionButton.setVisibility(View.GONE);

        return rootView;
    }

    private void addNewBook() {

        String title = etTittle.getText().toString();
        String author = etAuthor.getText().toString();
        String isbn = etIsbn.getText().toString().length() > 0 ?
                etIsbn.getText().toString() : "";

        String rating = (int) ratingBar.getRating() > 0 ?
                String.valueOf((int) ratingBar.getRating()) : "";

        Call<Void> addBook = bookServiceAPI.addBook( AppData.loggedUser.getUserId(),title,author,
                isbn,
                selectedBookCover,
                rating);

        addBook.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                Log.d("response", response.toString());
                Utilities.displayMessage("Dodano książkę", getActivity());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
                    final ArrayList<String> textArray = data.getStringArrayListExtra(OcrCaptureActivity.TextArray);
                    final ArrayAdapter<String> aa;
                    aa = new ArrayAdapter<>(  getContext(),
                            android.R.layout.simple_list_item_1,
                            textArray
                    );
                    lvOcrResults.setAdapter(aa);
                    lvOcrResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            etTittle.setText(textArray.get(i));
                        }
                    });
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getGoodreadsSearch(String title) {
        GoodreadsService.getSearchBook(title, requestQueue, new GoodreadsService.WeatherClientListener()
        {
            @Override
            public void onCityResponse(List<SearchResponse> city) {
                setAdapter(city);
            }
        });
    }

    private void setAdapter(final List<SearchResponse> searchResponses) {
        goodreadsAdapter = new GoodreadsAdapter(this.getContext(), (ArrayList<SearchResponse>) searchResponses);
        lvOcrResults.setAdapter(goodreadsAdapter);
        lvOcrResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etTittle.setText(searchResponses.get(i).getTitle());
                etAuthor.setText(searchResponses.get(i).getAuthor());
                selectedBookCover = searchResponses.get(i).getPath();
            }
        });
    }
}
