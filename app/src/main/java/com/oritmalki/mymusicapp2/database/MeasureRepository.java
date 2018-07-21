package com.oritmalki.mymusicapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oritmalki.mymusicapp2.AppExecutors;
import com.oritmalki.mymusicapp2.model.Measure;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Orit on 14.12.2017.
 */

public class MeasureRepository {


    private static MeasureRepository sInstance;
    private final AppDataBase mDatabase;
    private MediatorLiveData<List<Measure>> mObservableMeasures;
    private MediatorLiveData<List<Measure>>  mObservableMeasuresBySheet;
    private LiveData<List<Measure>> mMeasuresOfSheet;
    private MutableLiveData<List<Measure>> measuresBySheetMutable;


    //my addition
    public AppExecutors appExecutors = new AppExecutors();

    long mSheetId;


    //private constructor
    private MeasureRepository(final AppDataBase database, long sheetId) {

        mDatabase = database;

        mSheetId = sheetId;


        mObservableMeasures = new MediatorLiveData<>();
        mObservableMeasuresBySheet = new MediatorLiveData<>();

//       measuresBySheetMutable = new MutableLiveData<>();
//
//
//       observeMeasuresBySheet(measuresBySheetMutable, sheetId);

//Here we are keeping this instance as class variable so we can remove it later from mObservableMeasuresBySheet source
        mMeasuresOfSheet=mDatabase.measureDao().getMeasuresOfSheet(sheetId);
        mObservableMeasuresBySheet.addSource(mDatabase.measureDao().getMeasuresOfSheet(sheetId), new Observer<List<Measure>>() {

            @Override
            public void onChanged(@Nullable List<Measure> measuresBySheet) {
                Log.e("Repository", "measuresBySheet = " + measuresBySheet.toString() + " sheetId: " + sheetId);
                if (mDatabase.getDatabaseCreated().getValue() != null) {
                    appExecutors.diskIO().execute(() ->
                            mObservableMeasuresBySheet.postValue(measuresBySheet));
               }
            }
        });

//        mObservableMeasures.addSource(mDatabase.measureDao().getAll(), new Observer<List<Measure>>() {
//           @Override
//            public void onChanged(@Nullable List<Measure> allMeasures) {
//                if (mDatabase.getDatabaseCreated().getValue() != null) {
//                   appExecutors.diskIO().execute(() ->
//                            mObservableMeasures.postValue(allMeasures));
//               }
//            }
//        });


    }

    //new constructor from example
    public static MeasureRepository getInstance(final AppDataBase database, long sheetId) {
        if (sInstance == null) {
            synchronized (MeasureRepository.class) {
                if (sInstance == null) {
                    sInstance = new MeasureRepository(database, sheetId);
                }

            }
        }
//        if (sInstance.getSheetId() != sheetId) {
//            sInstance = new MeasureRepository(database, sheetId);
//        }
        return sInstance;
    }

    public void setSheetId(long SheetId) {
        this.mSheetId = SheetId;
    }

    public long getSheetId() {
        return mSheetId;
    }

    /*****Room Measures DAO*****/

    public void addNewMeasure(Measure measure, AtomicBoolean lock) {

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {

                if (!lock.get()) {
                    lock.set(true);
                    mDatabase.measureDao().newMeasure(measure);
                }
                lock.set(false);
            }


        });

        Log.d("ADD_MEASURE", "Added empty measure to database");

    }


    public void saveMeasures(List<Measure> measures) {
        appExecutors.diskIO().execute(() ->
                mDatabase.measureDao().insertAll(measures));
    }

    public LiveData<List<Measure>> getAllMeasures() {

        return mObservableMeasures;
    }

    public LiveData<List<Measure>> getMeasuresBySheet() {
        return mObservableMeasuresBySheet;
    }



    public void deleteAllMeasuresOfSheet(long sheetId) {
        appExecutors.diskIO().execute(() ->
                mDatabase.measureDao().deleteMeasuresOfSheet(sheetId));
    }

    public LiveData<Measure> getMeasure(int measureNum) {

        return mDatabase.measureDao().getMeasure(measureNum);
    }


    public void InsertMeasure(Measure measure) {

        mDatabase.measureDao().newMeasure(measure);
    }

    public void deleteMeasure(Measure measure) {
        appExecutors.diskIO().execute(() ->
                mDatabase.measureDao().delete(measure));
    }

    public void updateMeasure(Measure measure) {
        appExecutors.diskIO().execute(() ->
                mDatabase.measureDao().updateMeasure(measure));
    }

//    public LiveData<List<Beat>> getBeats(int measureNum) {
//        return mDatabase.measureDao().getBeats(measureNum);
//    }

    public void deleteAllMeasures() {
        appExecutors.diskIO().execute(() ->
                mDatabase.measureDao().deleteAll());
    }

    public void destroyDatabase() {

    }

    //Here we are updating the mObservableMeasuresBySheet with new source based on new sheetId
    public void updateSheetId(long sheetId) {
        if(mMeasuresOfSheet!=null) {
            mObservableMeasuresBySheet.removeSource(mMeasuresOfSheet);
        }
        mMeasuresOfSheet=mDatabase.measureDao().getMeasuresOfSheet(sheetId);
        mObservableMeasuresBySheet.addSource(mMeasuresOfSheet, new Observer<List<Measure>>() {
            @Override
            public void onChanged(@Nullable List<Measure> measuresBySheet) {
                if (mDatabase.getDatabaseCreated().getValue() != null) {
                    appExecutors.diskIO().execute(() ->
                            mObservableMeasuresBySheet.postValue(measuresBySheet));
                }
            }
        });
    }

    /*****Local App Memory DAO*****/


}
