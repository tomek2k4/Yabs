// IYabsService.aidl
package com.tomek.yabs.yabscommon;

// Declare any non-default types here with import statements

interface IYabsService {

    boolean beaconIdUpdate(long beaconId);
    String getUrl(long beaconId);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
     */
}
