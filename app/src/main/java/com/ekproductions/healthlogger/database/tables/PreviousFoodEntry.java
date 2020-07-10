package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "previous_food_entry")
public class PreviousFoodEntry extends FoodEntry {
    private int order;

    public PreviousFoodEntry(long id, Date date, String description, int calories, String entryType, int carbohydrates, int fat, int proteins, int order) {
        super(id, date, description, calories, entryType, carbohydrates, fat, proteins);
        this.order = order;
    }

    @Ignore
    public PreviousFoodEntry(FoodEntry entry, int order){
        super(0,entry.getDate(),
                entry.getDescription(),
                entry.getCalories(),
                entry.getEntryType(),
                entry.getCarbohydrates(),
                entry.getFat(),entry.getProteins());
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
