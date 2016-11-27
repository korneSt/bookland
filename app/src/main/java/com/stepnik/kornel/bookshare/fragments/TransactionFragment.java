package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.BookEvent;
import com.stepnik.kornel.bookshare.events.UserEvent;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.models.User;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookService;
import com.stepnik.kornel.bookshare.services.TransactionService;
import com.stepnik.kornel.bookshare.services.UserService;
import com.stepnik.kornel.bookshare.services.UserServiceAPI;

/**
 * Created by Iza on 17/11/2016.
 */

public class TransactionFragment extends Fragment {

    public OnBookSelectedListener mCallback;
    public OnUserSelectedListener mCallbackUser;

    public static final String ARG_TRANSACTION = "transaction";
    Transaction currTransaction;
    BookService bookService;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnBookSelectedListener) context;
            mCallbackUser = (OnUserSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_transaction,container,false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Transaction");
        TextView statusTv = (TextView) rootView.findViewById(R.id.transaction_status);
        Button getBook = (Button) rootView.findViewById(R.id.transaction_book);
        Button getOwner = (Button) rootView.findViewById(R.id.transaction_owner);
        Button getUser = (Button) rootView.findViewById(R.id.transaction_borrower);
        Button acceptButton = (Button) rootView.findViewById(R.id.b_accept);

        if (savedInstanceState != null) {
            currTransaction = (Transaction) savedInstanceState.getSerializable(ARG_TRANSACTION);
        } else {
            currTransaction = (Transaction) getArguments().getSerializable(ARG_TRANSACTION);
        }

        if (!(currTransaction.getOwnerId().equals(AppData.loggedUser.getUserId())) ||
                currTransaction.getStatus() != 1) {
            acceptButton.setVisibility(View.GONE);

        }

        statusTv.setText(setStatus(currTransaction.getStatus()));
        getBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BookService().getBook(currTransaction.getBookId());
            }
        });
        getOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserService().getUser(currTransaction.getOwnerId());
            }
        });
        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserService().getUser(currTransaction.getUserId());
            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TransactionService().acceptTransaction(currTransaction.getId());
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_TRANSACTION, currTransaction);
    }

    String setStatus(int status) {
        String returnMessage = "";
        switch (status) {
            case 1:
                returnMessage = "Czeka na akceptacje";
                break;
            case 2:
                returnMessage = "Zaakceptowano";
                break;
            case 3:
                returnMessage = "Ksiazka wydana";
                break;
            case 4:
                returnMessage = "Ksiazka oddana";
                break;
            case 5:
                returnMessage = "Transakcja ukonczona";
                break;
        }
        return returnMessage;
    }

    @Subscribe
    public void onBookResult(BookEvent event) {
        mCallback.onBookSelected(event.result.body(), false);
    }

    @Subscribe
    public void onUserResult(UserEvent event) {
        mCallbackUser.onUserSelected(event.result.body());
    }
}