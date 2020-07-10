package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "daily_note")
public class DailyNote {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date date;
    private String noteType;
    private String note;

    public DailyNote(long id, Date date, String noteType, String note) {
        this.id = id;
        this.date = date;
        this.noteType = noteType;
        this.note = note;
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

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
