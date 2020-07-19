package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "entry")
public class Entry {
    @PrimaryKey (autoGenerate = true)
    protected long id;
    protected Date date;
    protected String description;
    protected int calories;
    protected String entryType;

    public Entry(long id, Date date, String description, int calories, String entryType) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.calories = calories;
        this.entryType = entryType;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
}
