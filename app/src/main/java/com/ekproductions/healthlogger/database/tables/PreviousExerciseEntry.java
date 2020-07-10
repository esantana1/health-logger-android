package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "previous_exercise_entry")
public class PreviousExerciseEntry extends ExerciseEntry {
    private int order;

    public PreviousExerciseEntry(long id, Date date, String description, int calories, String entryType, int minutes, int order) {
        super(id, date, description, calories, entryType, minutes);
        this.order = order;
    }

    @Ignore
    public PreviousExerciseEntry(ExerciseEntry entry, int order){
        super(0,entry.getDate(),entry.getDescription(),entry.getCalories(),entry.getEntryType(), entry.getMinutes());
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
