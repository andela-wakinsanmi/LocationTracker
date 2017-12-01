package com.spykins.locationtracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.spykins.locationtracker.db.converter.DateConverter;
import com.spykins.locationtracker.model.GeoData;

@Database(entities = {GeoData.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class GeoDatabase extends RoomDatabase {
    public abstract GeoDataDao geoDataDao();

}
