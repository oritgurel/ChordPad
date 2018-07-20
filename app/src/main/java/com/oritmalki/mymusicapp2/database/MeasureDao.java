package com.oritmalki.mymusicapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.oritmalki.mymusicapp2.model.Measure;

import java.util.List;

/**
 * Created by Orit on 7.1.2018.
 */

@Dao
public interface MeasureDao {

    @Query("SELECT * FROM measure")
    LiveData<List<Measure>> getAll();

    @Query("SELECT * FROM measure where sheet_id=:sheetId")
     LiveData<List<Measure>> getMeasuresOfSheet(long sheetId);


    @Query("SELECT * FROM measure where measure_number LIKE :measureNumber")
     LiveData<Measure> getMeasure(int measureNumber);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertAll(List<Measure> measures);

    @Insert
     void newMeasure(Measure measure);

    @Query("DELETE FROM measure")
     void deleteAll();

    @Query("DELETE FROM measure where sheet_Id = :sheetId")
     void deleteMeasuresOfSheet(long sheetId);

    @Delete
     void delete(Measure measure);

    @Delete
     void delete(List<Measure> measures);

    @Update
     int updateMeasure(Measure measure);


    /*
    @Query("UPDATE measure SET beats = :beats where measure_number = :measureNumber")
    abstract void updateBeats(List<Beat> beats, int measureNumber);
    */

//    @Transaction
//    public void insertAndDeleteInTransaction(Measure newMeasure, Measure oldMeasure, List<Measure> measures) {
//        newMeasure(newMeasure);
//        delete(oldMeasure);
//        insertAll(measures);
//    }

}
