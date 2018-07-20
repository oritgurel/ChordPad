package com.oritmalki.mymusicapp2;

import android.app.Application;

import com.oritmalki.mymusicapp2.database.AppDataBase;
import com.oritmalki.mymusicapp2.database.MeasureRepository;
import com.oritmalki.mymusicapp2.database.SheetRepository;

/**
 * Created by Orit on 27.1.2018.
 */

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;



    private long mSheetId;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppExecutors = new AppExecutors();

    }
    public AppDataBase getDatabase() {
        return AppDataBase.getInstance(this, mAppExecutors);
    }


    public SheetRepository getSheetRepository() {
        return SheetRepository.getInstance(getDatabase());
    }

    public MeasureRepository getMeasureRepository() {
        return MeasureRepository.getInstance(getDatabase(), mSheetId);
    }

    public long getSheetId() {
        return mSheetId;
    }

    public void setSheetId(long mSheetId) {
        this.mSheetId = mSheetId;
    }

}
