package com.oritmalki.mymusicapp2.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orit on 28.11.2017.
 */
@Entity(tableName = "measure",
//        foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Sheet.class, parentColumns = "id", childColumns = "sheet_id"),
        indices = {@Index("id"), @Index(value = {"measure_number", "beats"}), @Index("sheet_id")})
public class Measure implements Serializable, Comparable<Measure> {

    @Embedded
    TimeSignature timeSignature;

    @NonNull
    @ColumnInfo(name = "sheet_id")
    public long sheetId;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "measure_number")
    public Integer measureNumber;


    @ColumnInfo(name = "time_signature")

    String timeSig;

    boolean showTimeSig = true;

    List<Beat> beats = new ArrayList<>();

    public Measure() {
        this.getBeats();
    }


    public Measure(int number, List<Beat> beats, TimeSignature timeSignature, boolean showTimeSig, long sheetId) {
        this.beats = beats;
        this.measureNumber = number;
        this.timeSignature = timeSignature;
        this.showTimeSig = showTimeSig;
        this.sheetId = sheetId;


        int s = beats.size();
        int x = timeSignature.getNumerator();
        if (timeSignature.numerator == x) {
            s = x;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSheetId() {
        return sheetId;
    }

    public void setSheetId(long sheetId) {
        this.sheetId = sheetId;
    }


    public List<Beat> getBeats() {
        return beats;
    }

    public int getNumber() {
        return measureNumber;
    }

    public void setNumber(int number) {
        this.measureNumber = number;
    }

    public void setBeats(List<Beat> beats) {
        this.beats = beats;
    }

    public void addBeat(Beat beat) {

        try {

            if (beats.size() <= timeSignature.getNumerator())
                beats.add(new Beat(" "));
        }
        catch (Exception e) {
            Log.v("BeatsException", "Too many beats.");

        }
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(int numerator, int denominator) {
        this.timeSignature = timeSignature;
    }

    public void removeBeat(int pos) {
        beats.remove(pos);
    }

    public boolean isShowTimeSig() {
        return showTimeSig;
    }

    public void setShowTimeSig(boolean showTimeSig) {
        this.showTimeSig = showTimeSig;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getTimeSig() {
        return timeSig;
    }

    public void setTimeSig(String timeSig) {
        this.timeSig = timeSig;
    }

    @Override
    public int compareTo(@NonNull Measure o) {
        int cmp = this.measureNumber.compareTo(o.measureNumber);
        if (cmp != 0) {
            return cmp;
        }
        boolean bool = this.getBeats().equals(o.getBeats());
        if (bool) {
            return 0;
        } else {
            return 1;
        }
    }
}


