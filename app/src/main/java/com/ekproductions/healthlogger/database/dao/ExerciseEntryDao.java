package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.ExerciseEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface ExerciseEntryDao {
    @Insert
    long insert(ExerciseEntry entry);

    @Update
    void update(ExerciseEntry entry);

    @Delete
    void delete(ExerciseEntry entry);

    @Query("DELETE FROM exercise_entry")
    void deleteAll();

    @Query("SELECT * FROM exercise_entry ORDER BY 'date' ASC")
    List<ExerciseEntry> getEntries();

    @Query("SELECT * FROM exercise_entry WHERE date('date') = date(:date) ORDER BY 'date' ASC")
    List<ExerciseEntry> getEntryByDate(Date date);

    @Query("SELECT * FROM exercise_entry")
    LiveData<List<ExerciseEntry>> getEntriesLive();

    @Query("SELECT * FROM exercise_entry WHERE date('date') = date(:date) ORDER BY 'date' ASC")
    LiveData<List<ExerciseEntry>> getEntryByDateLive(Date date);



}
