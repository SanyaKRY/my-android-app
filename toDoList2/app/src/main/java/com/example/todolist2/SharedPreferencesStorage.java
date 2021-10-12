package com.example.todolist2;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesStorage implements Storage {

    private static final String SHARED_PREFERENCES_STORAGE = "shared_preferences_storage";
    private static final String SHARED_PREFERENCES_STORAGE_KEY = "shared_preferences_storage_key";
    private static SharedPreferencesStorage instance;

    Context applicationContext;

    private SharedPreferencesStorage(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static SharedPreferencesStorage getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new SharedPreferencesStorage(applicationContext);
        }
        return instance;
    }

    @Override
    public void writeInStorage(ArrayList<Task> taskList) {
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences(SHARED_PREFERENCES_STORAGE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(SHARED_PREFERENCES_STORAGE_KEY, json);
        editor.apply();
    }

    @Override
    public ArrayList<Task> readFromStorage() {
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("shared_preferences_storage", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SHARED_PREFERENCES_STORAGE, null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> taskList = gson.fromJson(json, type);
        return taskList;
    }

}
