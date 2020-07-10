package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.ExerciseEntry;
import com.ekproductions.healthlogger.database.tables.FoodEntry;
import com.ekproductions.healthlogger.database.tables.PreviousFoodEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface PreviousFoodEntryDao {
    @Insert
    long insert(PreviousFoodEntry entry);

    @Update
    void update(PreviousFoodEntry entry);

    @Delete
    void delete(PreviousFoodEntry entry);

    @Query("DELETE FROM previous_food_entry")
    void deleteAll();

    @Query("SELECT * FROM previous_food_entry ORDER BY `order`")
    List<PreviousFoodEntry> getEntries();

    @Query("SELECT * FROM previous_food_entry ORDER BY `order`")
    LiveData<List<PreviousFoodEntry>> getEntriesLive();


}
