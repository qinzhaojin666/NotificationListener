package com.shizy.accessibilityservice;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.shizy.accessibilityservice.data.AppDatabase;
import com.shizy.accessibilityservice.data.entity.Record;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = NotificationListener.class.getSimpleName();

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        log("onListenerConnected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        log("onListenerDisconnected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        log("onNotificationPosted");
        if (!TextUtils.equals(sbn.getPackageName(), "com.tencent.mm")) {
            return;
        }
        Notification notification = sbn.getNotification();
        if (notification == null) {
            return;
        }

        Bundle extras = notification.extras;
        if (extras == null) {
            return;
        }

        final String title = extras.getString(Notification.EXTRA_TITLE);
        final String text = extras.getString(Notification.EXTRA_TEXT);

        log("title: " + title);
        log("text: " + text);

        ((App) getApplication()).getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getDatabase(getApplicationContext()).recordDao().insertRecord(new Record(title, text));
            }
        });
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        log("onNotificationRemoved");
    }

    private void log(String msg) {
        Log.e(TAG, msg);
    }
}
