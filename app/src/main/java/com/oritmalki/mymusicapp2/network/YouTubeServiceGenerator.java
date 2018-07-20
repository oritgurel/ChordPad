package com.oritmalki.mymusicapp2.network;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Orit on 26.1.2018.
 */

public class YouTubeServiceGenerator {

    static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    static Retrofit retrofitBuilder = new Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public YouTubeService youTubeService() {
        return retrofitBuilder.create(YouTubeService.class);
    }
}
