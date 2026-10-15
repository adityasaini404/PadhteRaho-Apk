package com.example.padhteraho.models;

public class Note {

    private String id;
    private String title;
    private String pdfUrl;
    private String iconUrl;


    public Note() {
        // Required for Firebase
    }


    public Note(String title, String pdfUrl, String iconUrl) {

        this.title = title;
        this.pdfUrl = pdfUrl;
        this.iconUrl = iconUrl;

    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getPdfUrl() {
        return pdfUrl;
    }


    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }


    public String getIconUrl() {
        return iconUrl;
    }


    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}