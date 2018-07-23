package com.oritmalki.mymusicapp2.firebase;

import com.oritmalki.mymusicapp2.model.Sheet;

public interface ISheet {
    void onSheetRecieved(Sheet sheet);
    void onError(String error);
}
