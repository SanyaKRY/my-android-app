package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "SettingsActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "SettingsActivity onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "SettingsActivity onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "SettingsActivity onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "SettingsActivity onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "SettingsActivity onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "SettingsActivity onRestart");
    }
}