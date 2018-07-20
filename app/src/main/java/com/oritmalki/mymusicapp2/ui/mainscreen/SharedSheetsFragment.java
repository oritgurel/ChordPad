package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;

import com.oritmalki.mymusicapp2.R;

import java.util.ArrayList;
import java.util.List;

public class SharedSheetsFragment extends android.support.v4.app.ListFragment {

    private static List<String> sharedSheets;

    public SharedSheetsFragment() {

    }

    public static SharedSheetsFragment getInstance() {
        return new SharedSheetsFragment();
    }

    public static void setData(List<String> sheets) {
        if (sharedSheets == null) {
            sharedSheets = new ArrayList<>(sheets);
        }
        if (!sharedSheets.isEmpty()) {
            sharedSheets.clear();
        }
        sharedSheets.addAll(sheets);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListShown(true);
        setEmptyText(getString(R.string.sharedsheets_fragment_empty_message));
    }

    @Override
    public void setEmptyText(CharSequence text) {
        super.setEmptyText(text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sharedSheets);
        if (sharedSheets != null) {
            setListAdapter(adapter);
        }


    }


}
