package com.tomek.yabs.yabsservice;

import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.tomek.yabs.yabsbeaconengine.BeaconEngineManager;
import com.tomek.yabs.yabsbeaconengine.EstimoteBeaconEngineManager;
import com.tomek.yabs.yabscloudclient.CloudClient;
import com.tomek.yabs.yabscloudclient.CloudClientException;
import com.tomek.yabs.yabscommon.IYabsService;

/**
 * Created by Tomek on 09.09.2014.
 */
public class IYabsServiceImpl extends IYabsService.Stub{

    private static final String TAG = "YabsService";

    private Context context;
    private BeaconEngineManager beaconEngineManager;

    public IYabsServiceImpl(Context context){
        Log.d(TAG,"Called constructor of IBeaconServiceImpl");
        this.context = context;
        beaconEngineManager = new EstimoteBeaconEngineManager(context);
        beaconEngineManager.initialize();
    }

    @Override
    public boolean beaconIdUpdate(long beaconId) throws RemoteException {

        try {
            new UpdateBeaconIdAsyncTask().execute(Long.valueOf(beaconId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private class UpdateBeaconIdAsyncTask extends AsyncTask<Long,Void,String>{

        private CloudClient cloudClient = new CloudClient("student","password");

        @Override
        protected String doInBackground(Long... params) {

            try {
                cloudClient.updateBeacon(params[0].longValue());
                return "Successfully sent HTTP command";
            } catch (CloudClientException e) {
                Log.e(TAG,"Failed to send HTTP request",e);
                e.printStackTrace();
                return "Failed to sent http request";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG,result);
        }
    }

    @Override
    public String getUrl(long beaconId) throws RemoteException {
        return null;
    }

    public void close() {
        beaconEngineManager.close();
    }
}
