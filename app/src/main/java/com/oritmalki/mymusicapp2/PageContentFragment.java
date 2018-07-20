package com.oritmalki.mymusicapp2.com.oritmalki.mymusicapp2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by Orit on 13.12.2017.
 */

public class PageContentFragment extends Fragment {

    private static final String ARGS_PAGE_NUMBER = "args_page_number";

    RelativeLayout Page;
    FlexboxLayout sheetContent;



    public static PageContentFragment newInstance(int pageNumber) {
        PageContentFragment pageContentFragment = new PageContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_PAGE_NUMBER, pageNumber);
        pageContentFragment.setArguments(bundle);
        return pageContentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pageNumber = getArguments().getInt(ARGS_PAGE_NUMBER);




    }
}
