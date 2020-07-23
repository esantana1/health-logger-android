package com.ekproductions.healthlogger.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ekproductions.healthlogger.database.HealthLoggerRepository;
import com.ekproductions.healthlogger.database.tables.UserSetting;
import com.ekproductions.healthlogger.database.tables.WeightLog;

import java.util.Date;
import java.util.List;

//TODO: Research best approach connect database with UI
public class HealthLoggerViewModel extends AndroidViewModel {
    private HealthLoggerRepository repository;
    private UserSetting userSettings;

    public HealthLoggerViewModel(@NonNull Application application) {
        super(application);
        repository = new HealthLoggerRepository(application);
        userSettings = repository.getUserSettings();
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

    //TODO: DailyNote model
    //TODO: ExcerciseEntry
    //TODO: FoodEntry
    //TODO: PreviousExerciseEntry
    //TODO: PreviousFoodEntry
    //TODO: WeightLog

}
