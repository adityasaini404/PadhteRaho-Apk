package com.example.padhteraho.models;

public class Playlist {

    private String id;
    private String courseId;
    private String courseName;
    private String title;
    private String teacher;
    private String playlistId;
    private String thumbnail;

    public Playlist() {}

    public Playlist(String id,
                    String courseId,
                    String courseName,
                    String title,
                    String teacher,
                    String playlistId,
                    String thumbnail) {

        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.title = title;
        this.teacher = teacher;
        this.playlistId = playlistId;
        this.thumbnail = thumbnail;
    }

    public String getId() { return id; }
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getTitle() { return title; }
    public String getTeacher() { return teacher; }
    public String getPlaylistId() { return playlistId; }
    public String getThumbnail() { return thumbnail; }

    public void setId(String id) { this.id = id; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setTitle(String title) { this.title = title; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public void setPlaylistId(String playlistId) { this.playlistId = playlistId; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
}