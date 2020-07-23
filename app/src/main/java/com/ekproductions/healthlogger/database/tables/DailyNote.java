package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "daily_note")
public class DailyNote {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date date;
    private String noteType;
    private String note;

    /**
     * Default Constructor for the Room framework. Do not use this constructor for implementation
     * purposes
     */
    public DailyNote(long id, Date date, String noteType, String note) {
        this.id = id;
        this.date = date;
        this.noteType = noteType;
        this.note = note;
    }

    /**
     * Create a new DailyNote record to be stored in a database. Use this constructor for
     * implementation purposes
     * @param date  - Date - to set for the record
     * @param noteType - String - note type value
     * @param note -  String - the actual note for the record
     */
    @Ignore
    public DailyNote( Date date, String noteType, String note) {
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
