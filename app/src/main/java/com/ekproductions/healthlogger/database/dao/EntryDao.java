package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.Entry;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    long insertEntry(Entry entry);

    @Update
    void updateEntry(Entry entry);

    @Query("SELECT * FROM entry ORDER BY date ASC")
    List<Entry> getEntries();

    @Query("SELECT * FROM entry ORDER BY date ASC")
    LiveData<List<Entry>> getEntriesLive();

    @Delete
    void deleteEntry(Entry entry);

    @Query("DELETE FROM entry")
    void deleteAll();


}
