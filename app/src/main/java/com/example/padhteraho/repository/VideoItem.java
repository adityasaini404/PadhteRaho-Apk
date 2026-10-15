package com.example.padhteraho.repository;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class VideoItem implements Parcelable {

    private String videoId;
    private String title;
    private String thumbnail;
    private String teacher;


    public VideoItem() {
    }

    public VideoItem(String videoId,
                     String title,
                     String thumbnail,
                     String teacher) {

        this.videoId = videoId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.teacher = teacher;
    }

    protected VideoItem(Parcel in) {
        videoId = in.readString();
        title = in.readString();
        thumbnail = in.readString();
        teacher = in.readString();
    }

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTeacher() {
        return teacher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(videoId);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(teacher);
    }
}