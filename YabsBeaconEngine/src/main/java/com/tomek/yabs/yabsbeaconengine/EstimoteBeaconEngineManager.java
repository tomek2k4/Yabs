package com.tomek.yabs.yabsbeaconengine;

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
public class EstimoteBeaconEngineManager extends BeaconEngineManager {

    private static final String TAG = "BeaconEngine";
    EstimoteBeaconManager estimoteBeaconManager;
    private Context context;

    public EstimoteBeaconEngineManager(Context context) {
        Log.d(TAG,"Estimote BeaconEngineManager Constuctor called");
        this.context = context;
    }

    @Override
    public boolean initialize() {
        boolean result = false;
        Log.d(TAG,"EstimoteBeaconEngineManager initialize called");
        estimoteBeaconManager = new EstimoteBeaconManager(context);
        estimoteBeaconManager.startEstiometeBeaconDetection();
        return result;
    }

    @Override
    public void close() {
        Log.d(TAG,"EstimoteBeaconEngineManager close called");
        estimoteBeaconManager.stopEstiometeBeaconDetection();
    }

    private class EstimoteBeaconManager extends BeaconManager implements BeaconManager.ServiceReadyCallback, BeaconManager.RangingListener{

        //estimote
        private final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
        //private static final String ESTIMOTE_PROXIMITY_UUID = "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0";
        private final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
        private static final int NOTIFICATION_ID = 123;
        private Region region;

        public EstimoteBeaconManager(Context context) {
            super(context);
            com.estimote.sdk.utils.L.enableDebugLogging(true);
            Log.d(TAG,"EstimoteBeaconManager constr called");
        }

        //estimote
        public void startEstiometeBeaconDetection() {
            Log.d(TAG,"EstimoteBeaconManager startEstiometeBeaconDetection called");
            this.connect(this);
            this.setRangingListener(this);
        }

        public void stopEstiometeBeaconDetection() {
            Log.d(TAG,"EstimoteBeaconManager stopEstiometeBeaconDetection called");
            this.disconnect();
        }


        @Override
        public void onServiceReady() {
            try {
                Log.d(TAG,"EstimoteBeaconManager onService ready callback called,start ranging");
                this.startRanging(ALL_ESTIMOTE_BEACONS);
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot start ranging", e);
            }
        }

        @Override
        public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
            Log.d(TAG, "EstimoteBeaconManager onBeaconsDiscovered callback called");
            Log.d(TAG,"Beacon size is: "+beacons.size());

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
    }
}
