package com.spykins.locationtracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.spykins.locationtracker.model.GeoData;

@Database(entities = {GeoData.class}, version = 1)
public abstract class GeoDatabase extends RoomDatabase {
    public abstract GeoDataDao geoDataDao();

}
