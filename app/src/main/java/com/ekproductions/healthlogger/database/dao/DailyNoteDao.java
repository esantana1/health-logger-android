package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.DailyNote;

import java.util.Date;
import java.util.List;

@Dao
public interface DailyNoteDao {
    @Insert
    long insert(DailyNote note);

    @Update
    void update(DailyNote note);

    @Delete
    void delete(DailyNote note);

    @Query("DELETE from daily_note")
    void  deleteAll();

    @Query("SELECT * FROM daily_note WHERE date('date') = date(:date) ORDER BY 'date' ASC")
    List<DailyNote> getNotesByDate(Date date);

    @Query("SELECT * FROM daily_note WHERE date('date') = date(:date) ORDER BY 'date' ASC")
    LiveData<List<DailyNote>> getNotesByDateLive(Date date);
}
