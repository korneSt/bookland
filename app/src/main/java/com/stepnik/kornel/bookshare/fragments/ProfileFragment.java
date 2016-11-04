package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.services.AppData;

import org.w3c.dom.Text;

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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tempTextView = (TextView) rootView.findViewById(R.id.temp_text);
        TextView tvUsername = (TextView) rootView.findViewById(R.id.tv_username);

        tempTextView.setText("Profile");
        tvUsername.setText(AppData.loggedUser.getUsername());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
