package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.ExerciseEntry;
import com.ekproductions.healthlogger.database.tables.FoodEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface FoodEntryDao {
    @Insert
    long insert(FoodEntry entry);

    @Update
    void update(FoodEntry entry);

    @Delete
    void delete(FoodEntry entry);

    @Query("DELETE FROM food_entry")
    void deleteAll();

    @Query("SELECT * FROM food_entry")
    List<FoodEntry> getEntries();

    @Query("SELECT * FROM food_entry WHERE DATE(date,'unixepoch') = DATE(:date,'unixepoch') ORDER BY date ASC")
    List<FoodEntry> getEntryByDate(Date date);

    @Query("SELECT * FROM food_entry ORDER BY date ASC" )
    LiveData<List<FoodEntry>> getEntriesLive();

    @Query("SELECT * FROM food_entry WHERE DATE(date,'unixepoch') = DATE(:date,'unixepoch') ORDER BY date ASC")
    LiveData<List<FoodEntry>> getEntryByDateLive(Date date);

    @Query("SELECT * FROM food_entry WHERE DATE(date,'unixepoch') = DATE(:date,'unixepoch') AND :type = entryType ORDER BY date ASC")
    LiveData<List<FoodEntry>> getEntryByDateAndTypeLive(Date date, String type);

    @Query("SELECT * FROM food_entry WHERE DATE(date,'unixepoch') = DATE(:date,'unixepoch') AND :type = entryType ORDER BY date ASC")
    List<FoodEntry> getEntryByDateAndType(Date date, String type);

}
