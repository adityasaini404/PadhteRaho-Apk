package com.example.padhteraho.repository;

import android.util.Log;

import com.example.padhteraho.BuildConfig;
import com.example.padhteraho.api.ApiClient;
import com.example.padhteraho.api.YoutubeApi;
import com.example.padhteraho.models.youtube.Item;
import com.example.padhteraho.models.youtube.PlaylistResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeRepository {

    public interface OnVideosLoaded {
        void onSuccess(List<VideoItem> videos);
        void onError(String message);
    }

    public void getPlaylistVideos(String playlistId,
                                  OnVideosLoaded listener) {

        YoutubeApi api = ApiClient
                .getClient()
                .create(YoutubeApi.class);

        List<VideoItem> allVideos = new ArrayList<>();

        loadPage(api, playlistId, "", allVideos, listener);
    }

    private void loadPage(YoutubeApi api,
                          String playlistId,
                          String pageToken,
                          List<VideoItem> allVideos,
                          OnVideosLoaded listener) {

        api.getPlaylistItems(
                "snippet",
                playlistId,
                50,
                pageToken,
                BuildConfig.YOUTUBE_API_KEY
        ).enqueue(new Callback<PlaylistResponse>() {

            @Override
            public void onResponse(Call<PlaylistResponse> call,
                                   Response<PlaylistResponse> response) {

                if (!response.isSuccessful()
                        || response.body() == null
                        || response.body().getItems() == null) {

                    listener.onError("No videos found.");
                    return;
                }

                PlaylistResponse body = response.body();

                for (Item item : body.getItems()) {

                    if (item.getSnippet() == null)
                        continue;

                    String videoId = "";

                    if (item.getSnippet().getResourceId() != null) {
                        videoId = item.getSnippet()
                                .getResourceId()
                                .getVideoId();
                    }

                    String title = item.getSnippet().getTitle();

                    String thumbnail = "";

                    if (item.getSnippet().getThumbnails() != null &&
                            item.getSnippet().getThumbnails().getHigh() != null) {

                        thumbnail = item.getSnippet()
                                .getThumbnails()
                                .getHigh()
                                .getUrl();
                    }

                    Log.d("VIDEO_ID", title);

                    allVideos.add(new VideoItem(
                            videoId,
                            title,
                            thumbnail,
                            ""
                    ));
                }

                if (body.getNextPageToken() != null
                        && !body.getNextPageToken().isEmpty()) {

                    loadPage(
                            api,
                            playlistId,
                            body.getNextPageToken(),
                            allVideos,
                            listener
                    );

                } else {

                    listener.onSuccess(allVideos);

                }

            }

            @Override
            public void onFailure(Call<PlaylistResponse> call,
                                  Throwable t) {

                listener.onError(t.getMessage());

            }
        });
    }
}