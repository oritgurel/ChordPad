package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.model.Sheet;
import com.oritmalki.mymusicapp2.viewmodel.SheetListViewModel;

import java.util.ArrayList;
import java.util.List;

public class SheetsActivity extends AppCompatActivity {

    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;
    private ViewPager mTabsViewPager;
    private TabLayout mTabLayout;

    private MySheetsFragment mMySheetsFragment;
    private SharedSheetsFragment mSharedSheetsFragment;

    private SheetListViewModel mSheetListViewModel;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SheetsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheets);
        initViews();
        initFragments();

        mSheetListViewModel = ViewModelProviders.of(this).get(SheetListViewModel.class);
        observeViewModel(mSheetListViewModel);


    }

    private void initViews() {
        mTabsViewPager = findViewById(R.id.sheets_viewpager);
        mTabLayout = findViewById(R.id.sheets_tab_layout);

        initFragments();

        SheetsViewPagerAdapter viewPagerAdapter = new SheetsViewPagerAdapter(getSupportFragmentManager(), mFragments, mFragmentTitles);
        mTabsViewPager.setAdapter(viewPagerAdapter);
        for (Fragment fragment : mFragments) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mFragmentTitles.get(mFragments.indexOf(fragment))));
        }
        mTabLayout.setupWithViewPager(mTabsViewPager);

    }

    private void initFragments() {

        mMySheetsFragment = MySheetsFragment.getInstance();
        mSharedSheetsFragment = SharedSheetsFragment.getInstance();

        mFragments = new ArrayList<>();
        mFragmentTitles = new ArrayList<>();
        mFragments.add(mMySheetsFragment);
        mFragmentTitles.add(getString(R.string.sheets_activity_my_sheets_tab_title));
        mFragments.add(mSharedSheetsFragment);
        mFragmentTitles.add(getString(R.string.sheets_activity_shared_with_me_tab_title));

    }

    private void observeViewModel(SheetListViewModel sheetListViewModel) {

        sheetListViewModel.getSheets().observe(this, new Observer<List<Sheet>>() {
            @Override
            public void onChanged(@Nullable List<Sheet> sheets) {

            }
        });
    }
    }

