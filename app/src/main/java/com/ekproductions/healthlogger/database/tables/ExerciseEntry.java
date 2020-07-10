package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "exercise_entry")
public class ExerciseEntry extends Entry {
    private int minutes;

    public ExerciseEntry(long id, Date date, String description, int calories, String entryType, int minutes) {
        super(id, date, description, calories, entryType);
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

}
