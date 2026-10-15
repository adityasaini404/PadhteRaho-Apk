package com.example.padhteraho.models.youtube;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaylistResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @SerializedName("nextPageToken")
    private String nextPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }
}