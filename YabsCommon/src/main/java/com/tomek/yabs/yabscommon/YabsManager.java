package com.tomek.yabs.yabscommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Tomek on 12.09.2014.
 */
public class YabsManager {


    private final static Intent YABS_SERVICE_INTENT =
            new Intent("com.tomek.yabs.yabscommon.IYabsService");
    private static final String TAG ="BeaconManager" ;

    private YabsServiceConnection YABS_SERVICE_CONNECTION = new YabsServiceConnection();
    IYabsService yabsService;

    Context context;

    /**
     * Constructor takes context of calling activity or service
     *
     * @param context of caller
     */
    public YabsManager(Context context) {

        context.bindService(YABS_SERVICE_INTENT, YABS_SERVICE_CONNECTION, Context.BIND_AUTO_CREATE);
        this.context = context;
    }

    /**
     * Updates beacon id
     *
     * @param beaconId to update
     * @return true or false
     */
    public boolean beaconIdUpdate(long beaconId){
        if(yabsService==null){
            Log.d(TAG,"Failed to send request to service, there is no beacon service");
            return false;
        }

        try {
            yabsService.beaconIdUpdate(beaconId);
            return true;
        } catch (RemoteException e) {
            Log.e("BeaconManager","Could not call remote service",e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  Closing Manager and unbinding from service
     */
    public void close(){
        Log.d(TAG, YabsManager.class.getSimpleName() + " close was called");
        context.unbindService(YABS_SERVICE_CONNECTION);
    }

    private class YabsServiceConnection implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            yabsService = IYabsService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            yabsService = null;

        }
    }
}
