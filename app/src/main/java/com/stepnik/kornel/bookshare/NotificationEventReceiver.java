package com.stepnik.kornel.bookshare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.MessageEvent;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.Message;
import com.stepnik.kornel.bookshare.services.TransactionService;
import com.stepnik.kornel.bookshare.services.TransactionServiceAPI;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 05.12.2016.
 */
public class NotificationEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 1;
    TransactionServiceAPI transactionServiceAPI = Data.retrofit.create(TransactionServiceAPI.class);

    public static void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date()),
                NOTIFICATIONS_INTERVAL_IN_HOURS * 10000,
                alarmIntent);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        final Intent[] serviceIntent = {null};
//        new TransactionService().getNewMessages(new Timestamp(1480471111111L));

        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());

        Call<List<Message>> newMessages = transactionServiceAPI.getNewMessages(currentTime);
        newMessages.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.body().size() > 0) {
                    String lastMessage = response.body().get(response.body().size() - 1).getMessage();

                    if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
                        serviceIntent[0] = NotificationService.createIntentStartNotificationService(context, lastMessage);
                    }
                    if (serviceIntent[0] != null) {
                        startWakefulService(context, serviceIntent[0]);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });

//        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
//            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
//            serviceIntent = NotificationService.createIntentStartNotificationService(context);
//        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
//            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
//            serviceIntent = NotificationService.createIntentDeleteNotification(context);
//        }
    }

    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}