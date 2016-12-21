package com.stepnik.kornel.bookshare.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.BookEvent;
import com.stepnik.kornel.bookshare.events.TransactionEvent;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.services.AppData;
import com.stepnik.kornel.bookshare.services.TransactionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by korSt on 17.11.2016.
 */

public class HistoryFragment extends Fragment{
    ListView lvTransactions;
    ArrayList<HashMap<String, String>> transactionList;
    ArrayList<Transaction> transactionData;
    OnTransactionSelectedListener mCallback;
    TransactionService transactionService;
    List<Transaction> transactionResults;
    ListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnTransactionSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_transactions,container,false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Transaction");



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            lvTransactions = (ListView) view.findViewById(R.id.lv_transactions);

            transactionList = new ArrayList<>();
            transactionData = new ArrayList<>();

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
        transactionService = new  TransactionService();
        transactionService.getHistoryTransactions(AppData.loggedUser.getUserId());
    }

    @Subscribe
    public void onTransactionEvent(TransactionEvent event) {
        transactionResults = event.result.body();
        for (Transaction t : transactionResults) {
            HashMap<String, String> tempTransaction = new HashMap<>();

            transactionData.add(t);
            tempTransaction.put("title", String.valueOf("User: " + t.getUserName()));
            tempTransaction.put("author", String.valueOf("My summary: " + t.getOwnerSummary()));
            transactionList.add(tempTransaction);
        }
        adapter = new SimpleAdapter(
                getContext(), transactionList,
                R.layout.list_item, new String[]{"title", "author"},
                new int[]{R.id.tv_item_primary, R.id.tv_item_secondary}
        );
        lvTransactions.setAdapter(adapter);
        lvTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onTransactionSelected(transactionData.get(i), false);
            }
        });
    }

    @Subscribe
    public void onBookEvent(BookEvent event) {
    }

}
