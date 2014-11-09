package com.tomek.yabs.yabsservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

/**
 * Created by Tomek on 09.09.2014.
 */
public class YabsService extends Service implements BeaconConsumer {

    private final static String TAG = "YabsService";
    private IYabsServiceImpl serviceImpl = null;

    BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service on create called");
        serviceImpl = new IYabsServiceImpl(getApplicationContext());
        Log.d(TAG,"Created IBeaconServiceImpl");
        beaconManager.bind(this);
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
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.d(TAG,"Connected to AltBeacon service");
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {
            Log.e(TAG,"Failed to start AltBecaon monitoring",e);

        }

    }
}
