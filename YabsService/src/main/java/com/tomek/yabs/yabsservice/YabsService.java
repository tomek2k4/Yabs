package com.tomek.yabs.yabsservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Tomek on 09.09.2014.
 */
public class YabsService extends Service {

    private final static String TAG = "YabsService";
    private IYabsServiceImpl serviceImpl = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service on create called");
        serviceImpl = new IYabsServiceImpl(getApplicationContext());
        Log.d(TAG,"Created IBeaconServiceImpl");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Service onBind called");
        return serviceImpl;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"Service onUnbind called");
        serviceImpl.close();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Service on Destroy called");
        serviceImpl = null;
    }
}
