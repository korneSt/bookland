package com.stepnik.kornel.bookshare.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;

/**
 * Created by Iza on 06/11/2016.
 */

public class HistoryFragment extends Fragment {

    ListView lvHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_history,container,false );

        lvHistory = (ListView) rootView.findViewById(R.id.lv_history);

        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("History");

        return rootView;
    }
}