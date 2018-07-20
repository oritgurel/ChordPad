package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.model.Sheet;
import com.oritmalki.mymusicapp2.viewmodel.MeasureListViewModel;
import com.oritmalki.mymusicapp2.viewmodel.SheetListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MySheetsFragment extends ListFragment {

    private static List<Sheet> mySheets;
    private Button mCreateNewBtn;
    private SheetListViewModel mSheetListViewModel;
    private MeasureListViewModel mMeasureListViewModel;

    public MySheetsFragment() {

    }

    public static MySheetsFragment getInstance() {
        return new MySheetsFragment();
    }

    public static void setData(List<Sheet> sheets) {
        if (mySheets == null) {
            mySheets = new ArrayList<>(sheets);
        }
        if (!mySheets.isEmpty()) {
            mySheets.clear();
        }
        mySheets.addAll(sheets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listfragment_mysheets_layout, container, false);
        mCreateNewBtn = view.findViewById(R.id.listfragment_mysheets_create_new_btn);
        mCreateNewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateNewSheetActivity.getIntent(getContext()));
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setListShown(true);
//        setEmptyText(getString(R.string.sharedsheets_fragment_empty_message));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCreateNewBtn.setVisibility(View.VISIBLE);

        mSheetListViewModel = ViewModelProviders.of(this).get(SheetListViewModel.class);
        mMeasureListViewModel = ViewModelProviders.of(this).get(MeasureListViewModel.class);

//        deleteAllSheets();

        mSheetListViewModel.getSheets().observe(getActivity(), new Observer<List<Sheet>>() {
                    @Override
                    public void onChanged(@Nullable List<Sheet> sheets) {

                        if (sheets == null) {
                            return;
                        }
                        setData(sheets);
                        ArrayList<String> titles = new ArrayList<>();
                        for (Sheet sheet : sheets) {
                            titles.add(sheet.getTitle());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, titles);
                        if (mySheets != null) {
                            setListAdapter(adapter);
//                            mCreateNewBtn.setVisibility(View.GONE);

                        }
                    }
                });


        }

        public void deleteAllSheets() {
            mSheetListViewModel.deleteAllSheets(getActivity().getApplication());
            mMeasureListViewModel.deleteAllMeasures(getActivity().getApplication());
        }

        public void deleteAllMeasuresOfSheet(long sheetId) {
            mMeasureListViewModel.deleteAllMeasuresOfSheet(getActivity().getApplication(), sheetId);
        }

        public void deleteSheet(long sheetId) {
            mSheetListViewModel.deleteSheet(getActivity().getApplication(), sheetId);
            deleteAllMeasuresOfSheet(sheetId);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Sheet selectedSheet = mySheets.get(position);
        startActivity(MainActivity.getIntent(getContext(), selectedSheet.getId(), selectedSheet.getTitle(), selectedSheet.getAuthor()));
    }
}
