package com.oritmalki.mymusicapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oritmalki.mymusicapp2.AppExecutors;
import com.oritmalki.mymusicapp2.model.Sheet;

import java.util.List;

public class SheetRepository {

    private static SheetRepository sInstance;
    private final AppDataBase mDatabase;
    private MediatorLiveData<List<Sheet>> mObservableSheets;

    public long id;

    //my addition
    public AppExecutors appExecutors = new AppExecutors();


    //private constructor
    private SheetRepository(final AppDataBase database) {

        mDatabase = database;


        mObservableSheets = new MediatorLiveData<>();

        mObservableSheets.addSource(mDatabase.sheetDao().getAll(), new Observer<List<Sheet>>() {
            @Override
            public void onChanged(@Nullable List<Sheet> sheets) {
                if (mDatabase.getDatabaseCreated().getValue() != null) {
                    appExecutors.diskIO().execute(() ->
                            mObservableSheets.postValue(sheets));

                }
            }
        });





    }

    //new constructor from example
    public static SheetRepository getInstance(final AppDataBase database) {
        if (sInstance == null) {
            synchronized (SheetRepository.class) {
                if (sInstance == null) {
                    sInstance = new SheetRepository(database);
                }
            }
        }
        return sInstance;
    }

    /*****Room Sheet DAO*****/

    public long addNewSheet(Sheet sheet, ISheetId listener) {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {

                listener.onIdRecieved(mDatabase.sheetDao().newSheet(sheet));
            }
        });

        Log.d("ADD_SHEET", "Added empty sheet to database");
        return getId();

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void addAllSheets(List<Sheet> sheets) {
        appExecutors.diskIO().execute(() ->
                mDatabase.sheetDao().insertAll(sheets));
    }

    public LiveData<List<Sheet>> getAllSheets() {

        return mObservableSheets;
    }

    public LiveData<Sheet> getSheet(long sheetId) {

        return mDatabase.sheetDao().getSheetById(sheetId);
    }



    public void deleteSheet(long sheetId) {
        appExecutors.diskIO().execute(() ->
                mDatabase.sheetDao().delete(sheetId));
    }

    public void deleteAllSheets() {
        appExecutors.diskIO().execute(() ->
                mDatabase.sheetDao().deleteAll());
    }

    public void updateSheet(Sheet sheet) {
          appExecutors.diskIO().execute(new Runnable() {

            @Override
            public void run() {
               mDatabase.sheetDao().updateSheet(sheet);
            }
        });

    }

//    public LiveData<List<Beat>> getBeats(int measureNum) {
//        return mDatabase.measureDao().getBeats(measureNum);
//    }

    public void destroyDatabase() {

    }
}
