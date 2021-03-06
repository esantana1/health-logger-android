package com.ekproductions.healthlogger.database.tables;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "weight_log")
public class WeightLog {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private float weight;
    private Date date;
    //TODO: Bonus: how to store Image?
    //private ?? image;

    /**
     * Constructor Used by Room API. Do do use in backend
     */
    public WeightLog(long id, float weight, Date date) {
        this.id = id;
        this.weight = weight;
        this.date = date;
    }

    /**
     * Backend constructor that creates a WeightLog with current date that it was created
     * @param weight Weight in Pounds
     */
    @Ignore
    public WeightLog(float weight){
        this.weight = weight;
        this.date = Calendar.getInstance().getTime();
    }
    @Ignore
    public WeightLog(float weight, Date date){
        this.weight = weight;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public float getWeight() {
        return weight;
    }

    public Date getDate() {
        return date;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

}
