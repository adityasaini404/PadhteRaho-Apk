package com.example.padhteraho.api;

import com.example.padhteraho.models.youtube.PlaylistResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApi {

    @GET("playlistItems")
    Call<PlaylistResponse> getPlaylistItems(

            @Query("part") String part,

            @Query("playlistId") String playlistId,

            @Query("maxResults") int maxResults,

            @Query("pageToken") String pageToken,

            @Query("key") String apiKey
    );

}