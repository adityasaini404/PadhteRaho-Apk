package com.example.padhteraho.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedManager {

    private static final String PREF = "saved_courses";

    public static boolean isSaved(Context context, String id) {

        SharedPreferences sp =
                context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        return sp.getBoolean(id, false);
    }

    public static void save(Context context, String id) {

        SharedPreferences sp =
                context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        sp.edit().putBoolean(id, true).apply();
    }

    public static void remove(Context context, String id) {

        SharedPreferences sp =
                context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        sp.edit().remove(id).apply();
    }

}