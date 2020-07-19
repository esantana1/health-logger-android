package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "food_entry")
public class FoodEntry extends Entry {

    protected int carbohydrates;
    protected int fat;
    protected int proteins;

    /**
     * Default Constructor for the Room framework. Do not use this constructor for implementation
     * purposes
     */
    public FoodEntry(long id, Date date, String description, int calories, String entryType, int carbohydrates, int fat, int proteins) {
        super(id, date, description, calories, entryType);
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.proteins = proteins;
    }

    /**
     * Create new food entry. Use this constructor for implementation purposes
     * @param date Date when the food was eaten
     * @param description Food description. For example an Apple
     * @param calories - Calories eaten
     * @param entryType - Entry type. for example, Breakfast, Lunch, Snack, etc.
     * @param carbohydrates - Carbs eaten.
     * @param fat - Fat eaten.
     * @param proteins - proteins eaten.
     */
    @Ignore
    public FoodEntry(Date date, String description, int calories, String entryType, int carbohydrates, int fat, int proteins) {
        super(0, date, description, calories, entryType);
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
