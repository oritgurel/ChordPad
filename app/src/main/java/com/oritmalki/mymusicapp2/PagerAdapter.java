package com.oritmalki.mymusicapp2.com.oritmalki.mymusicapp2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Orit on 13.12.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    List<PageContentFragment> fragments;

    public PagerAdapter(FragmentManager fm, List<PageContentFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
