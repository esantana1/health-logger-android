package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "previous_exercise_entry")
public class PreviousExerciseEntry extends ExerciseEntry {
    private int order;

    /**
     * Constructor used by Room API. Do not use this.
     */
    public PreviousExerciseEntry(long id, Date date, String description, int calories, String entryType, int minutes, int order) {
        super(id, date, description, calories, entryType, minutes);
        this.order = order;
    }

    /**
     * Backend constructor to create a new previous entry record with an order value of 1
     * @param entry Exercise entry to copy from
     */
    @Ignore
    public PreviousExerciseEntry(ExerciseEntry entry){
        super(0,entry.getDate(),entry.getDescription(),entry.getCalories(),entry.getEntryType(), entry.getMinutes());
        this.order = 1;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public  boolean isSameEntry(String description, String entryType, int calories, int minutes ){
        return description.equals(this.description) &&
                entryType.equals(this.entryType) &&
                calories == this.calories &&
                minutes == this.minutes;
    }
    public boolean isSameEntry(ExerciseEntry entry){
        return description.equals(entry.getDescription()) &&
                calories == entry.getCalories() &&
                entryType.equals(entry.getEntryType()) &&
                minutes == entry.getMinutes() ;

    }
}
