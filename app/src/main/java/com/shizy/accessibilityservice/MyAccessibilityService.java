package com.shizy.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = MyAccessibilityService.class.getSimpleName();

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        log("onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        log("onAccessibilityEvent: " + event);
    }

    @Override
    public void onInterrupt() {
        log("onInterrupt");
    }

    private void log(String msg) {
        Log.e(TAG, msg);
    }

}
