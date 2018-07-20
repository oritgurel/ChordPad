package com.oritmalki.mymusicapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.oritmalki.mymusicapp2.AppExecutors;
import com.oritmalki.mymusicapp2.model.Beat;
import com.oritmalki.mymusicapp2.model.Measure;
import com.oritmalki.mymusicapp2.model.Sheet;

import java.util.List;

/**
 * Created by Orit on 21.12.2017.
 */


@Database(entities = {Sheet.class, Measure.class, Beat.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "music_sheet";
    private static AppDataBase INSTANCE;

    private final static MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private static AppDataBase sInstance;

    public abstract SheetDao sheetDao();

    public abstract MeasureDao measureDao();

    public abstract BeatDao beatDao();

    public MeasureDao getMeasureDao() {
        return this.measureDao();
    }

    public BeatDao getBeatDao() {
        return this.beatDao();
    }

    public SheetDao getSheetDao() { return this.sheetDao(); }


    //my old getINSTANCE()
////    public static AppDataBase getINSTANCE(Context context) {
////        if (INSTANCE == null) {
////            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
////            List<Measure> measures = DemoContentGenerator.generateDemoContent();
////            insertData(AppDataBase.getINSTANCE(context), measures);
////
////        }
////        updateDatabaseCreated(context);
////        return INSTANCE;
//
//
//    }
//New version from MVVM github example
    public static AppDataBase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDataBase.class) {

                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return sInstance;
    }

    private static AppDataBase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDataBase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                                // Generate the data for pre-population
                        executors.diskIO().execute(() -> {

                            // Generate the data for pre-population
                            AppDataBase database = AppDataBase.getInstance(appContext, executors);
//                            List<Measure> measures = DemoContentGenerator.generateDemoContent();
//                            insertData(database, measures);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });

                    }
                }).fallbackToDestructiveMigration().build();
    }

    private static void insertData(final AppDataBase database, final List<Measure> measures) {
        database.runInTransaction(() -> {
            database.measureDao().insertAll(measures);

        });
    }



    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private static void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}


//    static final Migration MIGRATION_1_3 = new Migration(1, 3) {
//////        @Override
//////        public void migrate(SupportSQLiteDatabase database) {
//////            // Since we didn't alter the table, there's nothing else to do here.
//////        }
//////    };
////
////    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//                // Create the new table
//                database.execSQL(
//                        "CREATE TABLE Sheet_new (name TEXT, title TEXT, id INTEGER NOT NULL, author TEXT, PRIMARY KEY(id))");
//// Copy the data
//                database.execSQL(
//                        "INSERT INTO Sheet_new (name, title, id, author) SELECT name, title, SheetId, author FROM Sheet");
//// Remove the old table
//                database.execSQL("DROP TABLE Sheet");
//// Change the table name to the correct one
//                database.execSQL("ALTER TABLE Sheet_new RENAME TO Sheet");
//
//
//        // Create the new table
//                database.execSQL(
//                        "CREATE TABLE measure_new (numerator INTEGER, denominator INTEGER, measure_number INTEGER NOT NULL, sheetId INTEGER NOT NULL, showTimeSig INTEGER NOT NULL, PRIMARY KEY(measure_number), FOREIGN KEY(sheetId) REFERENCES Sheet (id) ON DELETE CASCADE)");
//// Copy the data
//                database.execSQL(
//                        "INSERT INTO measure_new (numerator, denominator, measure_number, sheetId, showTimeSig) SELECT numerator, denominator, measure_number, sheetId, showTimeSig FROM measure");
//// Remove the old table
//                database.execSQL("DROP TABLE measure");
//// Change the table name to the correct one
//                database.execSQL("ALTER TABLE measure_new RENAME TO measure");
//    }
//
//        static final Migration MIGRATION_3_4 = new Migration(3, 4) {
//            @Override
//            public void migrate(SupportSQLiteDatabase database) {
//                // Create the new table
//                database.execSQL(
//                        "CREATE TABLE Sheet_new (name TEXT, title TEXT, id INTEGER, author TEXT, PRIMARY KEY(id))");
//// Copy the data
//                database.execSQL(
//                        "INSERT INTO Sheet_new (name, title, id, author) SELECT name, title, SheetId, author FROM Sheet");
//// Remove the old table
//                database.execSQL("DROP TABLE Sheet");
//// Change the table name to the correct one
//                database.execSQL("ALTER TABLE Sheet_new RENAME TO Sheet");
//
//
//                // Create the new table
//                database.execSQL(
//                        "CREATE TABLE measure_new (numerator INTEGER, denominator INTEGER, measure_number INTEGER NOT NULL, sheetId INTEGER, showTimeSig INTEGER NOT NULL, PRIMARY KEY(measure_number), FOREIGN KEY(sheetId) REFERENCES Sheet (id) ON DELETE CASCADE)");
//// Copy the data
//                database.execSQL(
//                        "INSERT INTO measure_new (numerator, denominator, measure_number, sheetId, showTimeSig) SELECT numerator, denominator, measure_number, sheetId, showTimeSig FROM measure");
//// Remove the old table
//                database.execSQL("DROP TABLE measure");
//// Change the table name to the correct one
//                database.execSQL("ALTER TABLE measure_new RENAME TO measure");
//            }
//        };
//
//
//        };

