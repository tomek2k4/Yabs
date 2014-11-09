package com.tomek.yabs.yabsbeaconengine;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

/**
 * Created by Tomek on 16.10.2014.
 */
public class AltBeaconEngineManager extends BeaconEngineManager implements BeaconConsumer,MonitorNotifier{

    private static final String TAG = "BeaconEngine";
    private static final String BEACON_ID = "00000000-0000-0000-0000-000000000000";
    BeaconManager altBeaconManager;
    private Context context;

    public AltBeaconEngineManager(Context context) {
        Log.d(TAG,"AltBeaconEngineManager Constuctor called");
        this.context = context;
    }

    @Override
    public boolean initialize() {
        boolean result = true;
        Log.d(TAG,"AltBeaconEngineManager initialize called");
        altBeaconManager = BeaconManager.getInstanceForApplication(context);
        altBeaconManager.bind(this);
        return result;
    }

    @Override
    public void close() {
        Log.d(TAG,"AltBeaconEngineManager close called");
        altBeaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.d(TAG,"AltBeaconManager onService ready callback called,start ranging");
        altBeaconManager.setMonitorNotifier(this);
        try {
            altBeaconManager.startMonitoringBeaconsInRegion(new Region(BEACON_ID,null,null,null));
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "Cannot start ranging", e);
        }

    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG,"I just saw an beacon for the first time!");
    }

    @Override
    public void didExitRegion(Region region) {
        Log.d(TAG,"I no longer see an beacon");
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        Log.d(TAG, "I have just switched from seeing/not seeing beacons: "+state);
    }

    @Override
    public Context getApplicationContext() {
        return this.context;
    }

    @Override
    public void unbindService(ServiceConnection connection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection connection, int mode) {
        return false;
    }


}
