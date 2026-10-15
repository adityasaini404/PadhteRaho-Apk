package com.example.padhteraho.models;

public class Course {

    private String id;
    private String title;
    private String teacher;
    private String playlistId;
    private String category;
    private String duration;

    private boolean trending;
    private boolean recommended;
    private String description;
    private String thumbnail;
    private boolean saved;

    // Firebase empty constructor
    public Course() {}

    // MAIN constructor
    public Course(String id,
                  String title,
                  String teacher,
                  String playlistId,
                  String category,
                  String duration) {

        this.id = id;
        this.title = title;
        this.teacher = teacher;
        this.playlistId = playlistId;
        this.category = category;
        this.duration = duration;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getTeacher() { return teacher; }
    public String getPlaylistId() { return playlistId; }
    public String getCategory() { return category; }
    public String getDuration() { return duration; }

    public boolean isTrending() { return trending; }
    public boolean isRecommended() { return recommended; }

    public String getDescription() { return description; }
    public String getThumbnail() { return thumbnail; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public void setPlaylistId(String playlistId) { this.playlistId = playlistId; }
    public void setCategory(String category) { this.category = category; }
    public void setDuration(String duration) { this.duration = duration; }

    public void setTrending(boolean trending) { this.trending = trending; }
    public void setRecommended(boolean recommended) { this.recommended = recommended; }

    public void setDescription(String description) { this.description = description; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}