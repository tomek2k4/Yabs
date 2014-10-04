package com.tomek.yabs.yabsservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tomek on 13.09.2014.
 */
public class YabsServiceLoader extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log statement only to check if working properly, to be replaced
        Log.d("YabsService", "Received BOOT_COMPLETED broadcast, starting Yabs service");
        //Starting WatchdogService
        context.startService(new Intent(context, YabsService.class));
    }
}
