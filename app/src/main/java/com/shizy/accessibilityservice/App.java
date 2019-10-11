package com.shizy.accessibilityservice;

import android.app.Application;

public class App extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
        if (Utils.isNotificationListenerEnabled(this)) {
            Utils.requestRebind(this);
        }
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
