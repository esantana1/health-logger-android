package com.ekproductions.healthlogger.ui.dashboard;

import android.app.AlertDialog;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ekproductions.healthlogger.database.HealthLoggerRepository;
import com.ekproductions.healthlogger.database.tables.DailyNote;
import com.ekproductions.healthlogger.database.tables.ExerciseEntry;
import com.ekproductions.healthlogger.database.tables.FoodEntry;
import com.ekproductions.healthlogger.database.tables.UserSetting;
import com.ekproductions.healthlogger.database.tables.WeightLog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

//TODO: Research best approach connect database with UI
public class HealthLoggerViewModelCopy extends AndroidViewModel {
    private HealthLoggerRepository repository;
    private UserSetting userSettings;
    private LiveData<List<WeightLog>> weightLogs;
    private LiveData<List<ExerciseEntry>> exerciseEntries;
    private LiveData<List<FoodEntry>> breakfastFoodEntries;
    private LiveData<List<FoodEntry>> lunchFoodEntries;
    private LiveData<List<FoodEntry>> snacksFoodEntries;
    private LiveData<List<FoodEntry>> dinnerFoodEntries;
    private LiveData<List<DailyNote>> dailyNoteEntries;

    private int counter;


    public HealthLoggerViewModelCopy(@NonNull Application application) {
        super(application);
        repository = new HealthLoggerRepository(application);
//        try {
//            repository = new HealthLoggerRepository(application);
//        }
//        catch (Exception e){
//            AlertDialog.Builder builder = new AlertDialog.Builder(application);
//            builder.setMessage(e.getMessage());
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
        //TODO: Database code fix: this causes problems
//        userSettings = repository.getUserSettings();

        Date today = new Date();
        //initialize logs
        weightLogs = repository.getWeightLogsLive();
        exerciseEntries = repository.getExerciseEntriesByDateLive(today);
        breakfastFoodEntries = repository.getFoodEntriesByDateAndTypeLive(today,"BreakFast");
        lunchFoodEntries = repository.getFoodEntriesByDateAndTypeLive(today,"Lunch");
        snacksFoodEntries = repository.getFoodEntriesByDateAndTypeLive(today,"Snacks");
        dinnerFoodEntries = repository.getFoodEntriesByDateAndTypeLive(today,"Dinner");
        dailyNoteEntries = repository.getDailyNotesByDateLive(today);

    }

    //Prototype with UI
    public LiveData<List<FoodEntry>> getBreakFastFoodEntries(){
        return breakfastFoodEntries;
    }

    public LiveData<List<FoodEntry>>  updateByDate(Calendar calendar){
        breakfastFoodEntries = repository.getFoodEntriesByDateAndTypeLive(calendar.getTime(),"BreakFast");
        this.breakfastFoodEntries = breakfastFoodEntries;
        return  breakfastFoodEntries;
    }
    public void updatePreviousDate(Calendar calendar){

    }

    public void insertFoodEntry(Calendar calendar){
     repository.insertFoodEntry(new FoodEntry(calendar.getTime(), "Food" + ++counter, 100, "BreakFast",0,0,0) );
    }
    //-----------------
    public void getNextDayEntries(){

    }
    public  void getPreviousDayEntries(){

    }

    //User settings model
    public UserSetting getUserSettings (){
        userSettings = repository.getUserSettings();
        return userSettings;
    }
    public void updateUserSettings(UserSetting userSetting) throws RuntimeException{
        //If the setting is the same stored in model, then process it
        if(userSetting.equals(this.userSettings)){
            repository.updateSettings(userSettings);
        }
        else
            throw new RuntimeException
                    ("Only UserSetting object returned from getUserSettings" +
                            " method is allowed as a parameter.");

    }

    //TODO: WIP  - Methods for the table access

    //WeightLog model
    public List<WeightLog> getWeightLogs(){
        return repository.getWeightLogs();
    }
    public LiveData<List<WeightLog>> getWeightLogsLive(){return repository.getWeightLogsLive();}
    public List<WeightLog> getWeightLogsByDate(Date date){
        return repository.getWeightLogsByDate(date);
    }
    public LiveData<List<WeightLog>>getWeightLogsByDateLive(Date date){
        return repository.getWeightLogsByDateLive(date);
    }
    public void insertWeightLog(WeightLog log){ repository.insertWeightLog(log);}
    public void updateWeightLog (WeightLog log){repository.updateWeightLog(log);}
    public  void deleteWeightLog(WeightLog log){repository.deleteWeightLog(log);}

    public void deleteFoodEntry(FoodEntry current) {
        repository.deleteFoodEntry(current);
    }

    //TODO: DailyNote model
    //TODO: ExcerciseEntry
    //TODO: FoodEntry
    //TODO: PreviousExerciseEntry
    //TODO: PreviousFoodEntry
    //TODO: WeightLog

}
