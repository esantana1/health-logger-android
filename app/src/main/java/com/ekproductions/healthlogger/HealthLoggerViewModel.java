package com.ekproductions.healthlogger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ekproductions.healthlogger.database.HealthLoggerRepository;
import com.ekproductions.healthlogger.database.tables.UserSetting;
import com.ekproductions.healthlogger.database.tables.WeightLog;

import java.util.List;

public class HealthLoggerViewModel extends AndroidViewModel {
    private HealthLoggerRepository repository;
    private UserSetting userSettings;

    public HealthLoggerViewModel(@NonNull Application application) {
        super(application);
        repository = new HealthLoggerRepository(application);
        userSettings = repository.getSettings();
    }

    //User settings model
    public UserSetting getUserSettings (){
        userSettings = repository.getSettings();
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
    //WeightLog model
    public List<WeightLog> getWeightLogs(){
        return repository.getWeightLogs();
    }
    public void insertWeightLog(WeightLog log){ repository.insertWeightLog(log);}
    public void updateWeightLog (WeightLog log){repository.updateWeightLog(log);}
    public void deleteAllWeightLogs (){ repository.deleteAllWeightLogs();}
    public  void deleteWeightLog(WeightLog log){repository.deleteWeightLog(log);}

}
