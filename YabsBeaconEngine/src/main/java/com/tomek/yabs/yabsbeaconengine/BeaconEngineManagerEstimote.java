package com.tomek.yabs.yabsbeaconengine;

import android.app.NotificationManager;
import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

/**
 * Created by Tomek on 04.10.2014.
 */
public class BeaconEngineManagerEstimote extends BeaconEngineManager {

    //estimote
    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    //private static final String ESTIMOTE_PROXIMITY_UUID = "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0";
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
    private static final int NOTIFICATION_ID = 123;
    private static final String TAG = "BeaconEngine";
    private BeaconManager beaconManager;
    private NotificationManager notificationManager;
    private Region region;



    //estimote
    public void startEstiometeBeaconDetection(Context c) {
        notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        beaconManager = new BeaconManager(c);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {

                for(int i = 0; i < beacons.size(); i++ ) {
                    Log.d(TAG, String.format("Beacons[%d]: %s", i,  beacons.get(i).toString()));
                }

                for(int i = 0; i < beacons.size(); i++ ) {
                    Log.d(TAG, String.format("Beacons[%d]: %s", i,  beacons.get(i).toString()));
                    Log.d(TAG, String.format("Beacons[%d]: describeContents = %s", i,  beacons.get(i).describeContents()));
                    Log.d(TAG, String.format("Beacons[%d]: name = %s", i,  beacons.get(i).getName()));
                    Log.d(TAG, String.format("Beacons[%d]: hashCode = %d", i,  beacons.get(i).hashCode()));
                }
            }
        });
    }
    public void stopEstiometeBeaconDetection() {
        notificationManager.cancel(NOTIFICATION_ID);
        if(beaconManager != null)
            beaconManager.disconnect();
    }


}
