package com.oritmalki.mymusicapp2.model;

import android.arch.persistence.room.Relation;

import java.util.List;

public class SheetNameAndAllMeasures {

    public int id;
    public String name;
    @Relation(parentColumn = "id", entityColumn = "sheetId")
    public List<Measure> measures;
}
