package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.database.ISheetId;
import com.oritmalki.mymusicapp2.model.TimeSignature;
import com.oritmalki.mymusicapp2.viewmodel.SheetListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateNewSheetActivity extends AppCompatActivity {

    private SheetListViewModel mSheetListViewModel;

    @BindView(R.id.cs_create_sheet_btn)
    Button creatNewSheetBtn;
    @BindView(R.id.cs_activity_number_of_measures_et)
    TextInputEditText numberOfMeasuresEt;
    @BindView(R.id.cs_activity_sheet_timesig_num_et)
    TextInputEditText timeSigNum;
    @BindView(R.id.cs_activity_sheet_denom_num_et)
    TextInputEditText timeSigDenom;
    @BindView(R.id.cs_activity_sheet_title_et)
    TextInputEditText sheetTitleEt;
    @BindView(R.id.cs_activity_sheet_author_et)
    TextInputEditText sheetAuthorEt;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CreateNewSheetActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_sheet);
        ButterKnife.bind(this);

        mSheetListViewModel = ViewModelProviders.of(this).get(SheetListViewModel.class);

        creatNewSheetBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewSheet();
            }
        });
    }

//    @OnClick(R.id.cs_create_sheet_btn)
    private void createNewSheet() {
        String title = sheetTitleEt.getText().toString().trim();
        String author = sheetAuthorEt.getText().toString().trim();

        mSheetListViewModel.createNewSheet(getApplication(), Integer.valueOf(numberOfMeasuresEt.getText().toString().trim()) + 1,
                new TimeSignature(Integer.valueOf(timeSigNum.getText().toString().trim()), Integer.valueOf(timeSigDenom.getText().toString().trim())), title, author
                , new ISheetId() {
                    @Override
                    public void onIdRecieved(long id) {
                        startActivity(MainActivity.getIntent(getApplicationContext(), id, title, author));
                    }
                });


    }

}
