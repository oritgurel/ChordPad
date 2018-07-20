package com.oritmalki.mymusicapp2.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.oritmalki.mymusicapp2.BasicApp;
import com.oritmalki.mymusicapp2.model.Beat;
import com.oritmalki.mymusicapp2.model.Measure;
import com.oritmalki.mymusicapp2.model.TimeSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Orit on 27.1.2018.
 */

public class MeasureListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Measure>> mObservableMeasures;
    private final MediatorLiveData<List<Measure>> mObservableMeasuresBySheet;

    public MeasureListViewModel(Application application) {
        super(application);

        mObservableMeasures = new MediatorLiveData<>();
        mObservableMeasuresBySheet = new MediatorLiveData<>();


        // set by default null, until we get data from the database.
        mObservableMeasures.setValue(null);
        mObservableMeasuresBySheet.setValue(null);


        LiveData<List<Measure>> measures = ((BasicApp) application).getMeasureRepository()
                .getAllMeasures();

        // observe the changes of the measures from the database and forward them
        mObservableMeasures.addSource(measures, mObservableMeasures::setValue);

        LiveData<List<Measure>> measuresBySheet = ((BasicApp) application).getMeasureRepository()
                .getMeasuresBySheet();

        mObservableMeasuresBySheet.addSource(measuresBySheet, mObservableMeasuresBySheet::setValue);
    }


    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<Measure>> getMeasures() {
        return mObservableMeasures;
    }

    public LiveData<List<Measure>> getMeasuresBySheet(Application application) {
        return mObservableMeasuresBySheet;
    }

    public void deleteAllMeasuresOfSheet(Application application, long sheetId) {
        ((BasicApp) application).getMeasureRepository().deleteAllMeasuresOfSheet(sheetId);
    }

    public void deleteAllMeasures(Application application) {
        ((BasicApp) application).getMeasureRepository().deleteAllMeasures();
    }

    public void saveMeasures(Application application, List<Measure> measures) {
        ((BasicApp) application).getMeasureRepository().saveMeasures(measures);
    }

    public void updateMeasure(Application application, Measure measure) {
        ((BasicApp) application).getMeasureRepository().updateMeasure(measure);
    }

    public void addEmptyMeasure(Application application, long sheetId, AtomicBoolean lock) {

//        final CountDownLatch latch = new CountDownLatch(1);

        //prepare the time signature and beatList
        TimeSignature lastMesTimeSignature;
        if (mObservableMeasuresBySheet.getValue().size() != 0) {
            lastMesTimeSignature = mObservableMeasuresBySheet.getValue().get(mObservableMeasuresBySheet.getValue().size() - 1).getTimeSignature();
        } else {
            lastMesTimeSignature = new TimeSignature(4,4);
        }

        List<Beat> emptyBeats = new ArrayList<>();
        for (int i = 0; i < lastMesTimeSignature.getNumerator(); i++) {
            emptyBeats.add(new Beat("  "));
        }

        //insert empty measure
        if (mObservableMeasuresBySheet.getValue().size() != 0) {
            ((BasicApp) application).getMeasureRepository().addNewMeasure(new Measure(mObservableMeasuresBySheet.getValue().size() + 1, emptyBeats, lastMesTimeSignature, true, sheetId), lock);
        } else
            ((BasicApp) application).getMeasureRepository().addNewMeasure(new Measure(0, emptyBeats, lastMesTimeSignature, true, sheetId), lock);

    }


//TODO dao delete by sheet id

    public void deleteMeasure(Application application) {
        if (mObservableMeasuresBySheet.getValue().size() != 0) {
            ((BasicApp) application).getMeasureRepository().deleteMeasure(mObservableMeasuresBySheet.getValue().get(mObservableMeasuresBySheet.getValue().size() - 1));
        }

    }
}
