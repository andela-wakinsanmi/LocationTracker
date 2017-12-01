package com.spykins.locationtracker.db.converter;

import android.arch.persistence.room.TypeConverter;
import android.provider.SyncStateContract;

import com.spykins.locationtracker.AppConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    //static DateFormat df = new SimpleDateFormat(AppConstants.TIME_STAMP_FORMAT);

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
