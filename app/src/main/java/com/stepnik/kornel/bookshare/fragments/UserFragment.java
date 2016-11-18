package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.User;

import org.w3c.dom.Text;

/**
 * Created by Iza on 17/11/2016.
 */

public class UserFragment extends Fragment {

    public static final String ARG_USER = "user";
    private User selectetUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        final MainActivity mainActivity = (MainActivity) getContext();
        TextView username = (TextView) rootView.findViewById(R.id.user_username);
        TextView userReputation = (TextView) rootView.findViewById(R.id.user_reputation);
        ProgressBar userLevel = (ProgressBar) rootView.findViewById(R.id.user_progress);

        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            selectetUser = (User) savedInstanceState.getSerializable(ARG_USER);
        } else {
            selectetUser = (User) getArguments().getSerializable(ARG_USER);
        }

        mainActivity.setTitle(selectetUser.getUsername());
        username.setText(selectetUser.getUsername());
        userReputation.setText(String.valueOf(selectetUser.getRatePos()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
