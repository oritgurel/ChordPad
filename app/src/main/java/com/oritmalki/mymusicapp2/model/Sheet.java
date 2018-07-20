package com.oritmalki.mymusicapp2.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Orit on 21.12.2017.
 */
@Entity
public class Sheet implements Serializable {

    @PrimaryKey
    public Long id;

    public String title;
    public String author;


//    @Relation(parentColumn = "id", entityColumn = "measureNumber", entity = Measure.class)

    @Ignore
    public List<Measure> measures;


    public Sheet(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Sheet() {
        this.getMeasures();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    @Ignore
    public Sheet(Long id, String title, String author, List<Measure> measures) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.measures = measures;
    }
}
