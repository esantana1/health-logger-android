package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "food_entry")
public class FoodEntry extends Entry {

    private int carbohydrates;
    private int fat;
    private  int proteins;

    public FoodEntry(long id, Date date, String description, int calories, String entryType, int carbohydrates, int fat, int proteins) {
        super(id, date, description, calories, entryType);
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.proteins = proteins;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }
}
