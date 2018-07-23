package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.firebase.AuthManager;
import com.oritmalki.mymusicapp2.firebase.FbDatabaseManager;
import com.oritmalki.mymusicapp2.firebase.ISheets;
import com.oritmalki.mymusicapp2.model.Sheet;

import java.util.ArrayList;
import java.util.List;

public class SharedSheetsFragment extends android.support.v4.app.ListFragment {

    private static List<Sheet> sharedSheets;

    public SharedSheetsFragment() {

    }

    public static SharedSheetsFragment getInstance() {
        return new SharedSheetsFragment();
    }

    public static void setData(List<Sheet> sheets) {
        if (sharedSheets == null) {
            sharedSheets = new ArrayList<>(sheets);
        }
        if (!sharedSheets.isEmpty()) {
            sharedSheets.clear();
        }
        sharedSheets.addAll(sheets);
    }

    //TODO set the data to be a reading from firebase of the sheets that were shared with this user.


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

        FbDatabaseManager.getInstance().fetchSharedSheets(AuthManager.getFirebaseAuth().getUid(), new ISheets() {
            @Override
            public void onSheetsRecieved(List<Sheet> sheets) {

                SharedSheetsFragment.setData(sheets);

                if (sheets == null) {
                    return;
                }
                ArrayList<String> titles = new ArrayList<>();
                for (Sheet sheet : sheets) {
                    titles.add(sheet.getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sharedSheets);
                setListAdapter(adapter);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }
        });


    }

}


