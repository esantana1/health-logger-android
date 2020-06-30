package com.ekproductions.healthlogger.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.ekproductions.healthlogger.database.tables.UserSetting;

@Dao
public interface UserSettingDao {
    @Insert
    void insert(UserSetting userSetting);

    @Update
    void update(UserSetting userSetting);
}
