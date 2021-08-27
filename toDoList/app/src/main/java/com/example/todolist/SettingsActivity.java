package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "SettingsActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        setNavigationMenu();
    }

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private void setNavigationMenu() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_settings);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view_settings);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                if (id == R.id.nav_tasks) {
                    Toast.makeText(SettingsActivity.this, R.string.tasks,Toast.LENGTH_SHORT).show();
                    intent = new Intent(SettingsActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(SettingsActivity.this, R.string.settings,Toast.LENGTH_SHORT).show();
                    intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.sharedPrefRadioButton:
                Toast.makeText(getBaseContext(), R.string.shared_pref, Toast.LENGTH_SHORT).show();
                break;
            case R.id.externalRadioButton:
                Toast.makeText(getBaseContext(), R.string.external, Toast.LENGTH_SHORT).show();
                break;
            case R.id.internalRadioButton:
                Toast.makeText(getBaseContext(), R.string.internal, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlRadioButton:
                Toast.makeText(getBaseContext(), R.string.sql, Toast.LENGTH_SHORT).show();
                break;
        }
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