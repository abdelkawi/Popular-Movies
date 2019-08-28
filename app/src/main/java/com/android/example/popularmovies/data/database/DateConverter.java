package com.android.example.popularmovies.data.database;


import androidx.room.TypeConverter;

import java.util.Date;
import java.util.List;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long time){
        return time == null?null:new Date(time);
    }
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date==null?null:date.getTime();
    }


}
