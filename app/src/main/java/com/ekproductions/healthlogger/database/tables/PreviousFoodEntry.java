package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "previous_food_entry")
public class PreviousFoodEntry extends FoodEntry {
    private int order;

    /**
     * Constructor used by Room API. Do not use this.
     */
    public PreviousFoodEntry(long id, Date date, String description, int calories, String entryType, int carbohydrates, int fat, int proteins, int order) {
        super(id, date, description, calories, entryType, carbohydrates, fat, proteins);
        this.order = order;
    }

    /**
     * Backend constructor to create a new previous entry with order = 1.
     * @param entry Food entry to copy from
     */
    @Ignore
    public PreviousFoodEntry(FoodEntry entry){
        super(0,entry.getDate(),
                entry.getDescription(),
                entry.getCalories(),
                entry.getEntryType(),
                entry.getCarbohydrates(),
                entry.getFat(),entry.getProteins());
        this.order = 1;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isSameEntry(String description, int calories, String entryType, int carbohydrates, int fat, int proteins){
        return  description.equals(this.description) &&
                calories == this.calories &&
                entryType.equals(this.entryType) &&
                carbohydrates == this.carbohydrates &&
                fat == this.fat &&
                proteins == this.proteins;

    }

    public boolean isSameEntry(FoodEntry entry){
        return description.equals(entry.getDescription()) &&
                calories == entry.getCalories() &&
                entryType.equals(entry.getEntryType()) &&
                fat == entry.getFat() &&
                proteins == entry.getProteins();

    }

}
