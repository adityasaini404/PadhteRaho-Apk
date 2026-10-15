package com.example.padhteraho.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static final FirebaseDatabase database =
            FirebaseDatabase.getInstance();

    public static DatabaseReference getCategoriesRef() {
        return database.getReference("categories");
    }

    public static DatabaseReference getCoursesRef() {
        return database.getReference("courses");
    }

    public static DatabaseReference getChannelsRef() {
        return database.getReference("channels");
    }

    public static DatabaseReference getCourseRequestsRef() {
        return database.getReference("course_requests");
    }

    public static DatabaseReference getPlaylistsRef() {
        return database.getReference("playlists");
    }
}