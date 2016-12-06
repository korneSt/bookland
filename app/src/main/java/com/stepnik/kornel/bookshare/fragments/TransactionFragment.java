package com.stepnik.kornel.bookshare.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.Utilities;
import com.stepnik.kornel.bookshare.adapters.MessageAdapter;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.BookEvent;
import com.stepnik.kornel.bookshare.events.MessageEvent;
import com.stepnik.kornel.bookshare.events.UserEvent;
import com.stepnik.kornel.bookshare.models.Message;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.models.User;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.BookService;
import com.stepnik.kornel.bookshare.services.TransactionService;
import com.stepnik.kornel.bookshare.services.UserService;
import com.stepnik.kornel.bookshare.services.UserServiceAPI;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Iza on 17/11/2016.
 */

public class TransactionFragment extends Fragment {

    public static final String ARG_CLOSED = "closed";
    public OnBookSelectedListener mCallback;
    public OnUserSelectedListener mCallbackUser;
    public OnTransactionSelectedListener mCallbackTransaction;

    public static final String ARG_TRANSACTION = "transaction";
    Transaction currTransaction;
    ListView lvMessages;
    Boolean transactionClosed;
    private Handler h;
    private Runnable r;

    private ArrayList<Message> messages;
    private MessageAdapter messageAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnBookSelectedListener) context;
            mCallbackUser = (OnUserSelectedListener) context;
            mCallbackTransaction = (OnTransactionSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Listeners");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        final TransactionService transactionService = new TransactionService();
        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                transactionService.getMessages(currTransaction.getId());
                h.postDelayed(this, 5000);
            }
        };
        h.post(r);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        h.removeCallbacks(r);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_transaction,container,false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Transaction");

        if (savedInstanceState != null) {
            currTransaction = (Transaction) savedInstanceState.getSerializable(ARG_TRANSACTION);
            transactionClosed = savedInstanceState.getBoolean(ARG_CLOSED);
        } else {
            currTransaction = (Transaction) getArguments().getSerializable(ARG_TRANSACTION);
            transactionClosed = getArguments().getBoolean(ARG_CLOSED);
        }

        TextView statusTv = (TextView) rootView.findViewById(R.id.transaction_status);
        Button getBook = (Button) rootView.findViewById(R.id.transaction_book);
        Button getOwner = (Button) rootView.findViewById(R.id.transaction_owner);
        Button getUser = (Button) rootView.findViewById(R.id.transaction_borrower);
        Button acceptButton = (Button) rootView.findViewById(R.id.b_accept);
        Button closeTransaction = (Button) rootView.findViewById(R.id.b_close_transaction);
        final Button confirmReturn = (Button) rootView.findViewById(R.id.b_confirm_return);
        final Button confirmDelivery = (Button) rootView.findViewById(R.id.b_confirm_delivery);
        Button sendMessage = (Button) rootView.findViewById(R.id.b_send_message);
        lvMessages = (ListView) rootView.findViewById(R.id.lv_messages);
        EditText messageText = (EditText) rootView.findViewById(R.id.et_message);

        if (!transactionClosed){
            messageText.setVisibility(View.GONE);
            sendMessage.setVisibility(View.GONE);
        }
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(this.getContext(), messages);




        if (!(currTransaction.getOwnerId().equals(AppData.loggedUser.getUserId())) ||
                currTransaction.getStatus() != 1) {
            acceptButton.setVisibility(View.GONE);
        }
        if (currTransaction.getStatus() != 2 || currTransaction.getOwnerId().equals(AppData.loggedUser.getUserId())) {
            confirmDelivery.setVisibility(View.GONE);
        }
        if (currTransaction.getStatus() != 3 || !currTransaction.getOwnerId().equals(AppData.loggedUser.getUserId())) {
            confirmReturn.setVisibility(View.GONE);
        }
        if (currTransaction.getStatus() != 4) {
            closeTransaction.setVisibility(View.GONE);
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
                Utilities.displayMessage("Poczekaj na akceptacje właściciela", getActivity());
            }
        });
        confirmDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TransactionService().confirmTransaction(currTransaction.getId());
                confirmDelivery.setVisibility(View.GONE);
            }
        });
        confirmReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TransactionService().finalizeTransaction(currTransaction.getId());
                confirmReturn.setVisibility(View.GONE);
            }
        });
        final EditText finalMessageText = messageText;
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TransactionService().createMessage(currTransaction.getId(), AppData.loggedUser.getUserId(),
                        finalMessageText.getText().toString());
            }
        });
        closeTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbackTransaction.onCloseTransaction(currTransaction);
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
                returnMessage = "Transakcja zakonczona";
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

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (messages.size() != event.results.size()) {

            messageAdapter.clear();
            messageAdapter.addAll(event.results);
            lvMessages.setAdapter(messageAdapter);
            lvMessages.setSelection(event.results.size()-1);
        }
    }

}