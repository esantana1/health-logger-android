package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.WeightLog;

import java.util.Date;
import java.util.List;

@Dao
public interface WeightLogDao {
    @Query("SELECT * FROM weight_log ORDER BY date")
    List<WeightLog> getLogs();

    @Query("SELECT * FROM weight_log WHERE DATE(date,'unixepoch') = DATE(:date,'unixepoch') ORDER BY date ASC")
    List<WeightLog> getLogByDate(Date date);

    @Query("SELECT * FROM weight_log ORDER BY date ASC")
    LiveData<List<WeightLog>> getLogsLive();

    @Query("SELECT * FROM weight_log WHERE DATE(date,'unixepoch') = DATE(:date,'unixepoch') ORDER BY date ASC")
    LiveData<List<WeightLog>> getLogByDateLive(Date date);

    @Insert
    long insertLog(WeightLog log);

    @Update
    void updateLog (WeightLog log);

    @Delete
    void deleteLog(WeightLog log);

    @Query("DELETE FROM weight_log")
    void deleteAll();
}
