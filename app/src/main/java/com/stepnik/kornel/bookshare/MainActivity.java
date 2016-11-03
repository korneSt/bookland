package com.stepnik.kornel.bookshare;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stepnik.kornel.bookshare.fragments.AddBookFragment;
import com.stepnik.kornel.bookshare.fragments.BookDetailsFragment;
import com.stepnik.kornel.bookshare.fragments.MainFragment;
import com.stepnik.kornel.bookshare.fragments.MapFragment;
import com.stepnik.kornel.bookshare.fragments.MyBooksFragmnent;
import com.stepnik.kornel.bookshare.fragments.ProfileFragment;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.services.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapLongClickListener, MapFragment.OnFragmentInteractionListener, MyBooksFragmnent.OnBookSelectedListener{

    Button btnGetBook;
    ListView lvBooks;
    GoogleMap map;
    ArrayList<HashMap<String, String>> bookList;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.104:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    BookService bookService = retrofit.create(BookService.class);
    private ProgressDialog pDialog;
    MapFragment mapFragmentScreen;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass = null;

        fragmentClass = MainFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


//        btnGetBook = (Button) findViewById(R.id.btn_book);
//        lvBooks = (ListView) findViewById(R.id.lv_books);
//
//        bookList = new ArrayList<>();
//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//
//        btnGetBook.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // making json object request
////                makeJsonObjectRequest();
////                makeJsonArrayRequest();
//                getBooks();
//            }
//        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                try {
                    fragment = AddBookFragment.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.addToBackStack(fragment.getTag());
                transaction.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mapFragmentScreen = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_home) {
            fragmentClass = MainFragment.class;
        } else if (id == R.id.nav_profile) {
            fragmentClass = ProfileFragment.class;
        } else if (id == R.id.nav_mybooks) {
            fragmentClass = MyBooksFragmnent.class;
        } else if (id == R.id.nav_settings) {
            fragmentClass = MapFragment.class;
        } else if (id == R.id.nav_share) {

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51, 19)).title("Marker"));
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        map = googleMap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(),
                        marker.getTitle(),
                        Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        map.addMarker(new MarkerOptions().position(latLng).title("Book"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBookSelected(Book book) {
        Fragment fragment = null;
        try {
            fragment = BookDetailsFragment.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle args = new Bundle();
        args.putSerializable(BookDetailsFragment.ARG_BOOK, book);
//        args.putInt(BookDetailsFragment.ARG_POSITION, position);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(fragment.getTag());
        transaction.commit();

    }
}
