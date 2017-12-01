package com.spykins.locationtracker;

/**
 * Call back used by {@link AppManager} to write data into DataBase
 * Data is set from the SharedPreference {@link com.spykins.locationtracker.db.AppSharedPreference#writeTimeStampWhenExitFence}
 */
public interface AppManagerCallback {
    void setTimeDuration(long timeEntered, long timeLeft);
}
