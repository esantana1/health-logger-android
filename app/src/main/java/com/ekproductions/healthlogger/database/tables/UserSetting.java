package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;

@Entity(tableName = "user_setting")
public class UserSetting {
    private String userName;
    private float currentWeight;
    private int dailyCaloriesGoal;
    private float weightGoal;
    private boolean isFirstTime;

    public UserSetting(String userName, float currentWeight, float weightGoal, int dailyCaloriesGoal ) {
        this.currentWeight = currentWeight;
        this.dailyCaloriesGoal = dailyCaloriesGoal;
        this.weightGoal = weightGoal;
        this.userName = userName;
        this.isFirstTime = false;
    }

    public UserSetting getUserSetting(){return this;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getDailyCaloriesGoal() {
        return dailyCaloriesGoal;
    }

    public void setDailyCaloriesGoal(int dailyCaloriesGoal) {
        this.dailyCaloriesGoal = dailyCaloriesGoal;
    }

    public float getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(float weightGoal) {
        this.weightGoal = weightGoal;
    }
    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }
}
