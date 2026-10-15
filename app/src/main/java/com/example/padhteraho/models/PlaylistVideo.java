package com.example.padhteraho.models;

public class PlaylistVideo {

    private String videoId;
    private String title;
    private String duration;
    private String thumbnail;

    public PlaylistVideo() {
    }

    public PlaylistVideo(String videoId,
                         String title,
                         String duration,
                         String thumbnail) {

        this.videoId = videoId;
        this.title = title;
        this.duration = duration;
        this.thumbnail = thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}