package com.oritmalki.mymusicapp2.firebase;

import com.oritmalki.mymusicapp2.model.Sheet;

import java.util.List;

public interface ISheets {

    void onSheetsRecieved(List<Sheet> sheets);
    void onError(String error);
}
