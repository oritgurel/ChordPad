package com.oritmalki.mymusicapp2.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Orit on 19.11.2017.
 */
@Entity(tableName = "beat")
//        foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Measure.class, parentColumns = "measure_number", childColumns = "measureNum"))
public class Beat implements Serializable {

//    private int duration;
//    boolean isRest;

    @PrimaryKey
    @ColumnInfo(name = "chord_name")
    @NonNull
    private String chordName;

    public void setMeasureNum(Integer measureNum) {
        this.measureNum = measureNum;
    }

    private Integer measureNum;

    public Integer getMeasureNum() {
        return measureNum;
    }

    public Beat() {
        this.getChordName();
    }

    @Ignore
    public Beat(String chordName) {
        this.chordName = chordName;


    }

    public String getChordName() {
        return chordName;
    }

    public void setChordName(String chordName) {
        this.chordName = chordName;
    }
}
