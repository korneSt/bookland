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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.adapters.NewBooksAdapter;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookService;
import com.stepnik.kornel.bookshare.services.BookServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 31.10.2016.
 */

public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Book> newBooks;
    BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);
    private BookService bookService;

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

        bookService = new BookService();
        bookService.loadNewBooks();

    }

    @Subscribe
    public void onBooksEvent(NewBooksEvent event) {
        setAdapter(event.result.body());
    }

    @Override
    public void onStart() {
        super.onStart();
        ((NewBooksAdapter) mAdapter).setOnItemClickListener(new NewBooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LatLng pos = new LatLng(newBooks.get(position).getLocalLat(), newBooks.get(position).getLocalLon());
//                Toast.makeText(getActivity(), " was clicked!", Toast.LENGTH_SHORT).show();
                Marker bookMarker = googleMap.addMarker(new MarkerOptions().position(pos).title(newBooks.get(position).getTitle()).snippet("Marker Description"));

            }
        });
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

        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Home");

        newBooks = AppData.getBookList();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.books_recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewBooksAdapter(getActivity(), newBooks);
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

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.addMarker(new MarkerOptions().position(new LatLng(51, 19)).title("Marker Title").snippet("Marker Description"));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Class fragmentClass = MyBooksFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();

    }

    private void getNewBooks() {
        Call<List<Book>> books = bookServiceAPI.getBooks();
        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                Log.d("status", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    setAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    public void setAdapter(List<Book> books) {
        newBooks.addAll(books);
        mAdapter.notifyItemRangeChanged(0, books.size());
    }
}

