package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "exercise_entry")
public class ExerciseEntry extends Entry {
    protected int minutes;

    /**
     * Default Constructor for the Room framework. Do not use this constructor for implementation
     * purposes
     */
    public ExerciseEntry(long id, Date date, String description, int calories, String entryType, int minutes) {
        super(id, date, description, calories, entryType);
        this.minutes = minutes;
    }


    /**
     * Constructor for the ExerciseEntry record. Use this constructor for Implementation purposes.
     * @param date - Date to set to this record
     * @param description - Description to set to this record
     * @param calories - Calories burned to record in this record
     * @param entryType - Entry Type to set this record. For example, Cardio or Stretching, etc
     * @param minutes - Enter minutes spent on the exercise.
     */
    @Ignore
    public ExerciseEntry(Date date, String description, int calories, String entryType, int minutes) {
        super(0, date, description, calories, entryType);
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

}
