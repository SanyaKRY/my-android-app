package com.example.todolist2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public interface Storage {

    public static final String PREFS_FILE = "storage";
    public static final String STORAGE_TYPE = "storage_type";

    public static final String MEMORY_STORAGE = "memory_storage";
    public static final String INTERNAL_STORAGE = "internal_storage";
    public static final String EXTERNAL_STORAGE = "external_storage";
    public static final String SHARED_PREFERENCES = "shared_preferences";

    public default Storage getStorage(Context context) {
        Context applicationContext = context.getApplicationContext();
        SharedPreferences settings = applicationContext.getSharedPreferences(PREFS_FILE, context.MODE_PRIVATE);
        switch (settings.getString(STORAGE_TYPE, MEMORY_STORAGE)) {
            case (INTERNAL_STORAGE):
                return InternalStorage.getInstance(applicationContext);
            case (EXTERNAL_STORAGE):
                return ExternalStorage.getInstance(applicationContext);
            case (SHARED_PREFERENCES):
                return SharedPreferencesStorage.getInstance(applicationContext);
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract void writeInStorage(ArrayList<Task> taskList);

    public abstract ArrayList<Task> readFromStorage();

}
