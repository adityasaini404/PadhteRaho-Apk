package com.example.padhteraho.models.youtube;

public class Snippet {

    private String title;

    private ResourceId resourceId;

    private Thumbnails thumbnails;

    public String getTitle() {
        return title;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}