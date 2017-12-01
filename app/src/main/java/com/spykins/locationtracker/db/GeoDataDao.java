package com.spykins.locationtracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.spykins.locationtracker.model.GeoData;

import java.util.List;

@Dao
public interface GeoDataDao {
    @Query("select * FROM geodata")
    List<GeoData> fetchAllGeodata();

    @Insert
    public void insertDataInDb(GeoData geoData);

    @Insert
    public void insertAll(List<GeoData> geoData);
}
