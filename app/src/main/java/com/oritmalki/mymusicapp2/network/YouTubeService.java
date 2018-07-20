package com.oritmalki.mymusicapp2.network;

import com.oritmalki.mymusicapp2.network.youtubeapi.YoutubeSearchResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Orit on 26.1.2018.
 */

public interface YouTubeService {

/*Video search parameters*/
String QUERY = "q";


    /* api key */
    String apiKey = "AIzaSyAN8WXkskK81aD-bqTWowirAqofBWZ4uoU";
    String keyQuery= "?key=" + apiKey;

    @GET("search/?part=snippet&type=video" + keyQuery)
    Call<YoutubeSearchResults> getVideos(@Query(QUERY) String quaryValue);
}
