package com.ekproductions.healthlogger.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.UserSetting;

import java.util.List;

@Dao
public interface UserSettingDao {
    @Insert
    void insert(UserSetting userSetting);

    @Update
    void update(UserSetting userSetting);

    @Query("SELECT * from user_setting")
    List<UserSetting> getSettings();
}
