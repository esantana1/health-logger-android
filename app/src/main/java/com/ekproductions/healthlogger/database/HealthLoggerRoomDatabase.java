package com.ekproductions.healthlogger.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.ekproductions.healthlogger.database.dao.*;
import com.ekproductions.healthlogger.database.tables.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {
        UserSetting.class,
        WeightLog.class,
        Entry.class,
        ExerciseEntry.class,
        FoodEntry.class,
        PreviousExerciseEntry.class,
        PreviousFoodEntry.class,
        DailyNote.class},
        version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class HealthLoggerRoomDatabase extends RoomDatabase {

    public abstract UserSettingDao settingsDao();
    public abstract WeightLogDao weightLogDao();
    public abstract EntryDao entryDao();
    public abstract ExerciseEntryDao exerciseEntryDao();
    public abstract FoodEntryDao foodEntryDao();
    public abstract PreviousFoodEntryDao previousFoodEntryDao();
    public abstract PreviousExerciseEntryDao previousExerciseEntryDao();
    public abstract DailyNoteDao dailyNoteDao();

    private static volatile HealthLoggerRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static HealthLoggerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HealthLoggerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HealthLoggerRoomDatabase.class, "health_logger_database")
                           // .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static HealthLoggerRoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            UserSettingDao settingDao= INSTANCE.settingsDao();

            UserSetting setting = new UserSetting("temp");
            settingDao.insert(setting);

        }
    };



}
