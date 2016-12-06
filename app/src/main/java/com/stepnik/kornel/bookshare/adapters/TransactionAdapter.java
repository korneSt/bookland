package com.stepnik.kornel.bookshare.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.services.AppData;

import java.util.ArrayList;

/**
 * Created by korSt on 06.12.2016.
 */

public class TransactionAdapter extends ArrayAdapter<Transaction>{

    public TransactionAdapter(Context context, int resource) {
        super(context, resource);
    }
    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transaction = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_trans_title);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tv_trans_user);
        ImageView ivStatus = (ImageView) convertView.findViewById(R.id.iv_trans_status);

        if (AppData.loggedUser.getUserId().equals(transaction.getOwnerId())) {
            tvUser.setText(transaction.getUserName());
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGold));
        } else {
            tvUser.setText(transaction.getOwnerName());
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        int resourceId = R.drawable.ic_action_book;
        switch(transaction.getStatus()) {
            case 1:
                if (AppData.loggedUser.getUserId().equals(transaction.getOwnerId()))
                {
                    resourceId = R.drawable.info;
                } else {
                    resourceId = R.drawable.time;
                }
                break;
            case 2:
                resourceId = R.drawable.chat;
                break;
            case 3:
                resourceId = R.drawable.upload;
                break;
            case 4:
                resourceId = R.drawable.download;
                break;
        }
        tvTitle.setText(transaction.getTitle());
        ivStatus.setImageResource(resourceId);

        return convertView;
    }

}
