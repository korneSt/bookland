package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;

/**
 * Created by korSt on 31.10.2016.
 */

public class ProfileFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //TextView tempTextView = (TextView) rootView.findViewById(R.id.temp_text);
        //tempTextView.setText("Profile");
        final MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Profile");
        Button myBooks = (Button) rootView.findViewById(R.id.profile_mybooks);
        myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Fragment fragment = (Fragment) (MyBooksFragment.class).newInstance();
                    mainActivity.changeFragment(fragment);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        });

        Button search = (Button) rootView.findViewById(R.id.profile_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Fragment fragment = (Fragment) (SearchFragment.class).newInstance();
                    mainActivity.changeFragment(fragment);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        });

        Button history = (Button) rootView.findViewById(R.id.profile_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Fragment fragment = (Fragment) (HistoryFragment.class).newInstance();
                    mainActivity.changeFragment(fragment);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
