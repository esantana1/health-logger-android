package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.ExerciseEntry;
import com.ekproductions.healthlogger.database.tables.PreviousExerciseEntry;
import com.ekproductions.healthlogger.database.tables.PreviousFoodEntry;

import java.util.List;

@Dao
public interface PreviousExerciseEntryDao {
    @Insert
    long insert(PreviousExerciseEntry entry);

    @Update
    void update(PreviousExerciseEntry entry);

    @Delete
    void delete(PreviousExerciseEntry entry);

    @Query("DELETE FROM previous_exercise_entry")
    void deleteAll();

    @Query("SELECT * FROM previous_exercise_entry ORDER BY `order`")
    List<PreviousExerciseEntry> getEntries();

    @Query("SELECT * FROM previous_exercise_entry ORDER BY `order`")
    LiveData<List<PreviousExerciseEntry>> getEntriesLive();
}
