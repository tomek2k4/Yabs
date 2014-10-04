package com.tomek.yabs.yabsbeaconengine;

import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomek on 26.09.2014.
 */
public abstract class BeaconEngineManager {

    private static final String TAG = "BeaconEngine";
    /** The listeners list. */
    private List<BeaconEngineManagerListener> listeners = Collections
            .synchronizedList(new LinkedList<BeaconEngineManagerListener>());

    /** The lte status initialized. */
    private boolean beaconServiceInitialized = false;

    /** Current lte status. */
    private BeaconEngineStatus beaconEngineStatus;


    /**
     * Adds the beacon manager change listener.
     *
     * @param listener the listener
     * @return true, if successful
     */
    public boolean addBeaconEngineManagerListener(
            BeaconEngineManagerListener listener) {
        boolean result = listeners.add(listener);
        Log.d(TAG,"Beacon Manager change listener added. Number of listeners for Beacon Manager: "+listeners.size());
        return result;
    }

    /**
     * Removes the connection manager change listener.
     *
     * @param listener the listener
     * @return true, if successful
     */
    public boolean removeBeaconManagerListener(
            BeaconEngineManagerListener listener) {
        boolean result = listeners.remove(listener);

        if (result) {
            Log.d(TAG, "Beacon Manager listener removed. Number of listeners for Beacon Manager: " +
                    listeners.size());
        } else {
            Log.d(TAG, "Beacon Manager listener not found during removal attempt. Number of listeners for Connection Manager: " +
                    listeners.size());
        }
        return result;
    }


    /**
     * Removes the all beacon manager change listeners.
     */
    public void removeAllBeaconManagerListeners() {

        listeners.clear();

        Log.d(TAG,
                "All Beacon Manager change listeners removed. Number of listeners for Connection Manager: "
                        + listeners.size());
    }

    /**
     * Gets the listeners.
     *
     * @return the listeners
     */
    public List<BeaconEngineManagerListener> getListeners() {
        return listeners;
    }

    /**
     * getter for beacon service initialized
     * @return bool beacon service initialization status
     */
    public boolean isBeaconServiceInitialized() {
        return beaconServiceInitialized;
    }

    /**
     * seeter for beacon service initialization flag
     * @param beaconServiceInitialized
     */
    public void setBeaconServiceInitialized(boolean beaconServiceInitialized) {
        this.beaconServiceInitialized = beaconServiceInitialized;
    }

    /**
     * Notify status change.
     */
    public void notifyStatusChange() {
        synchronized (listeners) {
            for (BeaconEngineManagerListener listener : getListeners()) {
                listener.statusChanged(getBeaconEngineStatus());
            }

        }
    }

    public BeaconEngineStatus getBeaconEngineStatus() {
        return beaconEngineStatus;
    }

    /**
     * Sets the Beacon Engine status.
     *
     * @param beaconEngineStatus the lteStatus to set
     */
    public void setBeaconEngineStatus(BeaconEngineStatus beaconEngineStatus) {
        this.beaconEngineStatus = beaconEngineStatus;
    }






}
