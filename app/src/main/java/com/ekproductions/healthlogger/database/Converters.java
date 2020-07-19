package com.ekproductions.healthlogger.database;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value*1000);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime()/1000; //record in seconds
    }

    /**
     * Convert a Date object to a String Representation with date only
     * @param date Date object to convert string
     * @return a String date in the YYYY-MM-DD format.
     */
    public static String dateToDateOnlyString (Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return  date == null? null: format.format(date);
    }
}
