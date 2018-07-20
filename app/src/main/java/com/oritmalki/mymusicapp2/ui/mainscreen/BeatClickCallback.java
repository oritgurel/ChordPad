package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.view.View;

import com.oritmalki.mymusicapp2.model.Measure;

public interface BeatClickCallback {

    void onBeatClicked(Measure measure, View beatView, int beatPosition);
}
