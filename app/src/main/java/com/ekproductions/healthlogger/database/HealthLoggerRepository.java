package com.ekproductions.healthlogger.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ekproductions.healthlogger.database.dao.DailyNoteDao;
import com.ekproductions.healthlogger.database.dao.EntryDao;
import com.ekproductions.healthlogger.database.dao.ExerciseEntryDao;
import com.ekproductions.healthlogger.database.dao.FoodEntryDao;
import com.ekproductions.healthlogger.database.dao.PreviousExerciseEntryDao;
import com.ekproductions.healthlogger.database.dao.PreviousFoodEntryDao;
import com.ekproductions.healthlogger.database.dao.UserSettingDao;
import com.ekproductions.healthlogger.database.dao.WeightLogDao;
import com.ekproductions.healthlogger.database.tables.DailyNote;
import com.ekproductions.healthlogger.database.tables.ExerciseEntry;
import com.ekproductions.healthlogger.database.tables.FoodEntry;
import com.ekproductions.healthlogger.database.tables.PreviousExerciseEntry;
import com.ekproductions.healthlogger.database.tables.PreviousFoodEntry;
import com.ekproductions.healthlogger.database.tables.UserSetting;
import com.ekproductions.healthlogger.database.tables.WeightLog;

import java.util.Date;
import java.util.List;

public class HealthLoggerRepository {
    private UserSettingDao userSettingDao;
    private WeightLogDao weightLogDao;
    private EntryDao entryDao;
    private FoodEntryDao foodEntryDao;
    private ExerciseEntryDao exerciseEntryDao;
    private PreviousExerciseEntryDao previousExerciseEntryDao;
    private PreviousFoodEntryDao previousFoodEntryDao;
    private DailyNoteDao dailyNoteDao;
    private List<UserSetting> settings;

    public HealthLoggerRepository(Application application) {
        HealthLoggerRoomDatabase db = HealthLoggerRoomDatabase.getDatabase(application);
        userSettingDao = db.settingsDao();
        weightLogDao = db.weightLogDao();
        entryDao = db.entryDao();
        foodEntryDao = db.foodEntryDao();
        exerciseEntryDao = db.exerciseEntryDao();
        previousExerciseEntryDao = db.previousExerciseEntryDao();
        previousFoodEntryDao = db.previousFoodEntryDao();
        settings = userSettingDao.getSettings();
        dailyNoteDao = db.dailyNoteDao();
    }

    //User Settings methods
    /**
     * Return a UserSetting record containing what is currently stored. If the record does not
     * exist, it creates a new record into the database and returns it.
     * @return an UserSetting representing what is stored in the database.
     */
    public UserSetting getUserSettings() {
        // There is only one record in the settings table
        if (settings.size() > 0) {
            return settings.get(0);
        }
        // If non-existent, insert a new setting record in database and return it
        else {
            UserSetting newSettings = new UserSetting("temp");
            HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                    userSettingDao.insert(newSettings));

            return newSettings;
        }
    }

    /**
     * Update UserSettings into the database
     * @param newSettings - Usersettings containing new settings
     */
    public void updateSettings(UserSetting newSettings) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                userSettingDao.update(newSettings));
    }


    //WeightLog Repository
    public List<WeightLog> getWeightLogs() {
        return weightLogDao.getLogs();
    }

    public LiveData<List<WeightLog>> getWeightLogsLive() {
        return weightLogDao.getLogsLive();
    }

    public List<WeightLog> getWeightLogsByDate(Date date) {
        return weightLogDao.getLogByDate(date);
    }

    public LiveData<List<WeightLog>> getWeightLogsByDateLive(Date date) {
        return weightLogDao.getLogByDateLive(date);
    }


    public void updateWeightLog(WeightLog log) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                weightLogDao.updateLog(log));
    }

    public void insertWeightLog(WeightLog log) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                weightLogDao.insertLog(log));
    }

    public void deleteWeightLog(WeightLog log) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                weightLogDao.deleteLog(log));
    }

    // Food Entries Repository
    public List<FoodEntry> getFoodEntries() {
        return foodEntryDao.getEntries();
    }

    public List<FoodEntry> getFoodEntriesByDate(Date date) {
        return foodEntryDao.getEntryByDate(date);
    }

    public List<FoodEntry> getFoodEntriesByDateAndType(Date date, String type) {
        return foodEntryDao.getEntryByDateAndType(date, type);
    }

    public LiveData<List<FoodEntry>> getFoodEntriesByDateAndTypeLive(Date date, String type) {
        return foodEntryDao.getEntryByDateAndTypeLive(date, type);
    }

    public LiveData<List<FoodEntry>> getFoodEntriesLive() {
        return foodEntryDao.getEntriesLive();
    }

    public LiveData<List<FoodEntry>> getFoodEntriesByDateLive(Date date) {
        return foodEntryDao.getEntryByDateLive(date);
    }

    public void deleteFoodEntry(FoodEntry foodEntry) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                foodEntryDao.delete(foodEntry));
    }

    public void updateFoodEntry(FoodEntry foodEntry) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            foodEntryDao.update(foodEntry);
            insertPreviousFoodEntry(foodEntry);
        });
    }

    /**
     *
     * @param foodEntry FoodEntry to be inserted into the database
     */
    public void insertFoodEntry(FoodEntry foodEntry) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            foodEntryDao.insert(foodEntry);
            insertPreviousFoodEntry(foodEntry);
        });
    }


    /**
     *
     * Insert a new Food entry to the previous Food Entries list if that entry is new. If inserted,
     * all other records in the list will have its order field updated to an increment of 1 and
     * delete the record with order of 10. The to be inserted record will have an order value of 1.
     *
     * @param entry - entry to be inserted to the list if it is new from
     *              the last 10 entries
     */
    public void insertPreviousFoodEntry(FoodEntry entry) {

        List<PreviousFoodEntry> list = previousFoodEntryDao.getEntries();
        PreviousFoodEntry newEntry = new PreviousFoodEntry(entry);
        if (list.size() == 0)
            previousFoodEntryDao.insert(newEntry);
        else {
            boolean isNew = false;
            for (PreviousFoodEntry current : list) {
                if (!current.isSameEntry(entry)) {
                    isNew = true;
                    break;
                }
            }
            if (isNew) {
                //update all records in the list with increment of 1 on order and  then
                // remove the "11'th" field
                for (PreviousFoodEntry current : list) {
                    current.setOrder(current.getOrder() + 1);
                    if (current.getOrder() > 10)
                        previousFoodEntryDao.delete(current);
                    else
                        previousFoodEntryDao.update(current);
                }
                //insert new previous entry
                previousFoodEntryDao.insert(newEntry);
            }
        }
    }


    // Exercise Entries Repository
    public List<ExerciseEntry> getExerciseEntries() {
        return exerciseEntryDao.getEntries();
    }

    public List<ExerciseEntry> getExerciseEntriesByDate(Date date) {
        return exerciseEntryDao.getEntryByDate(date);
    }

    public List<ExerciseEntry> getExerciseEntriesByDateAndType(Date date, String type) {
        return exerciseEntryDao.getEntryByDateAndType(date, type);
    }

    public LiveData<List<ExerciseEntry>> getExerciseEntriesByDateAndTypeLive(Date date, String type) {
        return exerciseEntryDao.getEntryByDateAndTypeLive(date, type);
    }

    public LiveData<List<ExerciseEntry>> getExerciseEntriesLive() {
        return exerciseEntryDao.getEntriesLive();
    }

    public LiveData<List<ExerciseEntry>> getExerciseEntriesByDateLive(Date date) {
        return exerciseEntryDao.getEntryByDateLive(date);
    }

    public void deleteExerciseEntry(ExerciseEntry entry) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                exerciseEntryDao.delete(entry));
    }

    public void updateFoodEntry(ExerciseEntry entry) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseEntryDao.update(entry);
            insertPreviousExerciseEntry(entry);
        });
    }

    public void insertExerciseEntry(ExerciseEntry entry) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseEntryDao.insert(entry);
            insertPreviousExerciseEntry(entry);
        });
    }

    /**
     *
     * Insert a new Exercise entry to the previous Exercise Entries list if that entry is new. If inserted,
     * all other records in the list will have its order field updated to an increment of 1 and
     * delete the record with order of 10. The to be inserted record will have an order value of 1.
     *
     * @param entry - entry to be inserted to the list if it is new from
     *              the last 10 entries
     */
    public void insertPreviousExerciseEntry(ExerciseEntry entry) {
        List<PreviousExerciseEntry> list = previousExerciseEntryDao.getEntries();
        PreviousExerciseEntry newEntry = new PreviousExerciseEntry(entry);
        if (list.size() == 0)
            previousExerciseEntryDao.insert(newEntry);
        else {
            boolean isNew = false;
            for (PreviousExerciseEntry current : list) {
                if (!current.isSameEntry(entry)) {
                    isNew = true;
                    break;
                }
            }
            if (isNew) {
                //update all records in the list with increment of 1 on order and  then
                // remove the "11'th" field
                for (PreviousExerciseEntry current : list) {
                    current.setOrder(current.getOrder() + 1);
                    if (current.getOrder() > 10)
                        previousExerciseEntryDao.delete(current);
                    else
                        previousExerciseEntryDao.update(current);
                }
                //insert new previous entry
                previousExerciseEntryDao.insert(newEntry);
            }
        }
    }


    // PreviousExercise Entries Repository
    public List<PreviousExerciseEntry> getPreviousExerciseEntries() {
        return previousExerciseEntryDao.getEntries();
    }

    public LiveData<List<PreviousExerciseEntry>> getPreviousExerciseEntriesLive() {
        return previousExerciseEntryDao.getEntriesLive();
    }

    // Previous Food Entries Repository
    public List<PreviousFoodEntry> getPreviousFoodEntries() {
        return previousFoodEntryDao.getEntries();
    }

    public LiveData<List<PreviousFoodEntry>> getPreviousFoodEntriesLive() {
        return previousFoodEntryDao.getEntriesLive();
    }

    //Daily Note Repository

    public void updateDailyNote(DailyNote note) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                dailyNoteDao.update(note));
    }

    public void insertDailyNote(DailyNote note) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                dailyNoteDao.insert(note));
    }

    public void deleteDailyNote(DailyNote note) {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                dailyNoteDao.delete(note));
    }

    public List<DailyNote> getDailyNotesByDate(Date date) {
        return dailyNoteDao.getNotesByDate(date);
    }

    public LiveData<List<DailyNote>> getDailyNotesByDateLive(Date date) {
        return dailyNoteDao.getNotesByDateLive(date);
    }


    //Getters for Daos

    public UserSettingDao getUserSettingDao() {
        return userSettingDao;
    }

    public WeightLogDao getWeightLogDao() {
        return weightLogDao;
    }

    public EntryDao getEntryDao() {
        return entryDao;
    }

    public FoodEntryDao getFoodEntryDao() {
        return foodEntryDao;
    }

    public ExerciseEntryDao getExerciseEntryDao() {
        return exerciseEntryDao;
    }

    public PreviousExerciseEntryDao getPreviousExerciseEntryDao() {
        return previousExerciseEntryDao;
    }

    public PreviousFoodEntryDao getPreviousFoodEntryDao() {
        return previousFoodEntryDao;
    }

    public DailyNoteDao getDailyNoteDao() {
        return dailyNoteDao;
    }

    /**
     * Clear the database so it be a first time use of the app. Use this in the UI Thread(s).
     */
    public void deleteAllDataUI() {
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
        {
            _deleteAllData();
        });

    }
    public void deleteAllData(){
        _deleteAllData();
    }
    private void _deleteAllData(){
        exerciseEntryDao.deleteAll();
        entryDao.deleteAll();
        dailyNoteDao.deleteAll();
        exerciseEntryDao.deleteAll();
        foodEntryDao.deleteAll();
        previousFoodEntryDao.deleteAll();
        previousExerciseEntryDao.deleteAll();
        weightLogDao.deleteAll();

        UserSetting userSetting = this.getUserSettings();
        userSetting.setFirstTime(true);
        userSetting.setUserName("temp");
        userSetting.setCurrentWeight(0);
        userSetting.setDailyCaloriesGoal(0);
        userSetting.setWeightGoal(0);
        userSettingDao.update(userSetting);
    }
}
