package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.adapters.NewBooksAdapter;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.services.AppData;
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

public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener{

    MapView mMapView;
    private GoogleMap googleMap;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Book> newBooks;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http:/192.168.1.3:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    BookService bookService = retrofit.create(BookService.class);


    @Override
    public void onResume() {
        super.onResume();
//        getNewBooks();
    }

    @Override
    public void onStart() {
        super.onStart();
//        getNewBooks();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.books_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
        newBooks = AppData.getBookList();
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewBooksAdapter(getActivity(), newBooks);
//        mAdapter = new NewBooksAdapter(newBooks);
        mRecyclerView.setAdapter(mAdapter);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        getNewBooks();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51,19)).title("Marker Title").snippet("Marker Description"));

    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        Class fragmentClass = MyBooksFragmnent.class;
        Fragment fragment = null;
    try {
         fragment = (Fragment) fragmentClass.newInstance();
    } catch (Exception e) {
        e.printStackTrace();
    }
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();

    }

    private void getNewBooks() {
        Call<List<Book>> books = bookService.getBooks();
        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, retrofit2.Response<List<Book>> response) {

                Log.d("status", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    newBooks.addAll(response.body());
//                    mAdapter.setNewBooksList();
                    mAdapter.notifyItemRangeChanged(0, response.body().size());
//                    for (Book book : response.body()) {
//                       newBooks.add(book);
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }
}

