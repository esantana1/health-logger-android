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

    public HealthLoggerRepository(Application application){
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

    public UserSetting getSettings(){
        // There is only one record in the settings table
        if(settings.size()> 0){
            return settings.get(0);
        }
        // If non-existent, insert a new setting record in database and return it
        else{
            UserSetting newSettings = new UserSetting("temp");
            HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                    userSettingDao.insert(newSettings));

            return newSettings;
        }
    }

    public void updateSettings(UserSetting newSettings){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                userSettingDao.update(newSettings));
    }


    //WeightLog Repository
    public List<WeightLog> getWeightLogs(){
        return weightLogDao.getLogs();
    }
    public LiveData<List<WeightLog>> getWeightLogsLive(){
        return weightLogDao.getLogsLive();
    }
    public List<WeightLog> getWeightLogsByDate(Date date){return weightLogDao.getLogByDate(date);}
    public LiveData<List<WeightLog>> getWeightLogsByDateLive(Date date){return weightLogDao.getLogByDateLive(date);}


    public void updateWeightLog (WeightLog log){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
            weightLogDao.updateLog(log));
    }

    public  void insertWeightLog (WeightLog log){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                weightLogDao.insertLog(log));
    }

    public  void deleteAllWeightLogs (){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                weightLogDao.deleteAll());
    }
    public  void deleteWeightLog ( WeightLog log){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                weightLogDao.deleteLog(log));
    }

    // Food Entries Repository
    public List<FoodEntry> getFoodEntries(){return foodEntryDao.getEntries();}
    public List<FoodEntry> getFoodEntriesByDate(Date date){return foodEntryDao.getEntryByDate(date);}
    public LiveData<List<FoodEntry>> getFoodEntriesLive(){return foodEntryDao.getEntriesLive();}
    public LiveData<List<FoodEntry>> getFoodEntriesByDateLive(Date date){return foodEntryDao.getEntryByDateLive(date);}
    public  void deleteFoodEntry ( FoodEntry foodEntry){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                foodEntryDao.delete(foodEntry));
    }
    public  void updateFoodEntry ( FoodEntry foodEntry){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            foodEntryDao.update(foodEntry);
            updatePreviousFoodEntries(foodEntry);
        });
    }

    public  void insertFoodEntry ( FoodEntry foodEntry){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            foodEntryDao.insert(foodEntry);
            updatePreviousFoodEntries(foodEntry);
        });
    }
    public  void deleteAllFoodEntry (){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                foodEntryDao.deleteAll());
    }

    //TODO
    private void updatePreviousFoodEntries(FoodEntry entry){

    }


    // Exercise Entries Repository
    public List<ExerciseEntry> getExerciseEntries(){return exerciseEntryDao.getEntries();}
    public List<ExerciseEntry> getExerciseEntriesByDate(Date date){return exerciseEntryDao.getEntryByDate(date);}
    public LiveData<List<ExerciseEntry>> getExerciseEntriesLive(){return exerciseEntryDao.getEntriesLive();}
    public LiveData<List<ExerciseEntry>> getExerciseEntriesByDateLive(Date date){return exerciseEntryDao.getEntryByDateLive(date);}
    public  void deleteExerciseEntry ( ExerciseEntry entry){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                exerciseEntryDao.delete(entry));
    }
    public  void updateFoodEntry ( ExerciseEntry entry){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseEntryDao.update(entry);
            updatePreviousExerciseEntries(entry);
        });
    }

    public  void insertExerciseEntry ( ExerciseEntry entry){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseEntryDao.insert(entry);
            updatePreviousExerciseEntries(entry);
        });
    }
    public  void deleteAllExerciseEntry (){
        HealthLoggerRoomDatabase.databaseWriteExecutor.execute(() ->
                exerciseEntryDao.deleteAll());
    }

    //TODO
    private void updatePreviousExerciseEntries(ExerciseEntry entry){

    }


    // PreviousExercise Entries Repository
    public List<PreviousExerciseEntry> getPreviousExerciseEntries(){
        return previousExerciseEntryDao.getEntries();
    }

    public LiveData<List<PreviousExerciseEntry>> getPreviousExerciseEntriesLive(){
        return previousExerciseEntryDao.getEntriesLive();
    }

    // Previous Food Entries Repository
    public List<PreviousFoodEntry> getPreviousFoodEntries(){
        return previousFoodEntryDao.getEntries();
    }

    public LiveData<List<PreviousFoodEntry>> getPreviousFoodEntriesLive(){
        return previousFoodEntryDao.getEntriesLive();
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
}
