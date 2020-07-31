package com.ekproductions.healthlogger.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ekproductions.healthlogger.database.HealthLoggerRepository;
import com.ekproductions.healthlogger.database.tables.FoodEntry;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private HealthLoggerRepository repository;
    private LiveData<List<FoodEntry>> breakfastFoodEntries;


    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}