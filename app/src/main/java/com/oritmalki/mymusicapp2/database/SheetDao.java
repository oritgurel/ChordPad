package com.oritmalki.mymusicapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.oritmalki.mymusicapp2.model.Sheet;

import java.util.List;

@Dao
public interface SheetDao {
    @Query("SELECT * FROM sheet")
    LiveData<List<Sheet>> getAll();

    @Query("SELECT * FROM sheet where title LIKE :sheetTitle")
    LiveData<Sheet> getSheetByTitle(String sheetTitle);

    @Query("SELECT * FROM sheet where author LIKE :author")
    LiveData<Sheet> getSheetByAuthor(String author);

    @Query("SELECT * FROM sheet where id LIKE :id")
    LiveData<Sheet> getSheetById(long id);

    @Insert
    long newSheet(Sheet sheet);

    @Insert
    long[] insertAll(List<Sheet> sheets);

    @Query("DELETE FROM sheet where id like :sheetId")
    int delete(long sheetId);

    @Query("DELETE FROM sheet")
    int deleteAll();

    @Update
    int updateSheet(Sheet sheet);





}
