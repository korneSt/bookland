package com.stepnik.kornel.bookshare.fragments;

import android.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.stepnik.kornel.bookshare.*;
import com.stepnik.kornel.bookshare.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentHome extends Fragment {

    // Progress dialog
    private ProgressDialog pDialog;

    Button btnGetBook;
    ListView lvBooks;
    //    ArrayList<Book> bookList;
    ArrayList<HashMap<String, String>> bookList;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.128.64.162:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    BookService bookService = retrofit.create(BookService.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_home,container,false );

        //NAD TYM MUSZE JESZCZE SIE ZASTANOWIC BO WYKRZACZA

//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        btnGetBook = (Button) rootView.findViewById(R.id.btn_book);
        btnGetBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
//                makeJsonObjectRequest();
//                makeJsonArrayRequest();
                getBooks();
            }
        });

        lvBooks = (ListView) rootView.findViewById(R.id.lv_books);

        bookList = new ArrayList<>();
        pDialog = new ProgressDialog( (MainActivity)getActivity() );
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        return rootView;
    }




    private void getBooks() {
        Call<List<Book>> books = bookService.getBooksYear();

        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, retrofit2.Response<List<Book>> response) {
                bookList = new ArrayList<>();

                for (Book book : response.body()) {
                    HashMap<String, String> bookTemp = new HashMap<>();
                    bookTemp.put("title", book.getTitle());
                    bookTemp.put("year", book.getAuthor());
                    bookList.add(bookTemp);
                }

                ListAdapter adapter = new SimpleAdapter(
                        (MainActivity)getActivity(), bookList,
                        R.layout.list_item, new String[]{"title", "year"},
                        new int[]{R.id.tv_title, R.id.tv_year}
                );

                lvBooks.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
