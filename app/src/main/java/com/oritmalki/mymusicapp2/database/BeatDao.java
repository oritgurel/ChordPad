package com.oritmalki.mymusicapp2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.oritmalki.mymusicapp2.model.Beat;

import java.util.List;

/**
 * Created by Orit on 8.1.2018.
 */
@Dao
public interface BeatDao {

    @Query("SELECT * FROM beat where measureNum LIKE :measureNum")
    List<Beat> getBeatsFromMeasure(int measureNum);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Beat> beats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBeat(Beat beat);


    @Delete
    void delete(Beat beat);
}

