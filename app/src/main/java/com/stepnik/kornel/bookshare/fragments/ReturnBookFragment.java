package com.stepnik.kornel.bookshare.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.Utilities;
import com.stepnik.kornel.bookshare.adapters.MessageAdapter;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookService;
import com.stepnik.kornel.bookshare.services.TransactionService;
import com.stepnik.kornel.bookshare.services.UserService;

import java.util.ArrayList;

/**
 * Created by korSt on 31.10.2016.
 */

public class ReturnBookFragment extends Fragment{

    public static final String ARG_TRANSACTION = "transaction";
    private Transaction currTransaction;


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        final TransactionService transactionService = new TransactionService();

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_return_book, container, false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Transaction");
        final EditText editTextFeedback = (EditText) rootView.findViewById(R.id.et_feedback);
        Button buttonReturn = (Button) rootView.findViewById(R.id.b_return);
        final RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.rb_user_rating);

        if (savedInstanceState != null) {
            currTransaction = (Transaction) savedInstanceState.getSerializable(ARG_TRANSACTION);
        } else {
            currTransaction = (Transaction) getArguments().getSerializable(ARG_TRANSACTION);
        }

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TransactionService().closeTransaction(currTransaction.getId(),
                        editTextFeedback.getText().toString(),
                        ratingBar.getNumStars(),
                        AppData.loggedUser.getUserId().equals(currTransaction.getOwnerId()));
            }
        });

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_TRANSACTION, currTransaction);
    }
}
