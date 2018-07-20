package com.oritmalki.mymusicapp2.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oritmalki.mymusicapp2.model.Beat;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Orit on 8.1.2018.
 */

public class Converters {



    @TypeConverter
    public static List<Beat> stringToBeats(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Beat>>() {}.getType();
        List<Beat> beats = gson.fromJson(json, type);
        return beats;
    }

    @TypeConverter
    public static String beatsToString(List<Beat> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Beat>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
