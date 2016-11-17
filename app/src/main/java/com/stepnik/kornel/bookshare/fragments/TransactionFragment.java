package com.stepnik.kornel.bookshare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;

/**
 * Created by Iza on 17/11/2016.
 */

public class TransactionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_transaction,container,false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Transaction");

        return rootView;
    }
}