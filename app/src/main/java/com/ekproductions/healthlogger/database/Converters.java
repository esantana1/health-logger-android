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
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    //TODO
    public static String dateToTimeStampDateOnly (Date date){
        ///DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        //String date

        //return  date == null? null: format.format(date);

    return null;
    }
}
