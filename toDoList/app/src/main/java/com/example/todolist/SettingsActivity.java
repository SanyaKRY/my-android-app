package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
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

        loadStorageConfiguration();
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
        RadioButton radioButton;
        switch(view.getId()) {
            case R.id.sharedPrefRadioButton:
                radioButton = (RadioButton) findViewById(R.id.sharedPrefRadioButton);
                storageTypeTextByRadioButton = radioButton.getText().toString();
                storageTypeIdByRadioButton = radioButton.getId();
                Toast.makeText(getBaseContext(), R.string.shared_pref, Toast.LENGTH_SHORT).show();
                break;
            case R.id.externalRadioButton:
                if (checkStoragePermission()) {
                    radioButton = (RadioButton) findViewById(R.id.externalRadioButton);
                    storageTypeTextByRadioButton = radioButton.getText().toString();
                    storageTypeIdByRadioButton = radioButton.getId();
                } else {
                    radioButton = (RadioButton) findViewById(R.id.externalRadioButton);
                    radioButton.setChecked(false);
                    if (storageTypeIdByRadioButton != -1) {
                        ((RadioButton) findViewById(storageTypeIdByRadioButton)).setChecked(true);
                    }
                    requestStoragePermission();
                }

                Toast.makeText(getBaseContext(), R.string.external, Toast.LENGTH_SHORT).show();
                break;
            case R.id.internalRadioButton:
                radioButton = (RadioButton) findViewById(R.id.internalRadioButton);
                storageTypeTextByRadioButton = radioButton.getText().toString();
                storageTypeIdByRadioButton = radioButton.getId();
                Toast.makeText(getBaseContext(), R.string.internal, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlRadioButton:
                radioButton = (RadioButton) findViewById(R.id.sqlRadioButton);
                storageTypeTextByRadioButton = radioButton.getText().toString();
                storageTypeIdByRadioButton = radioButton.getId();
                Toast.makeText(getBaseContext(), R.string.sql, Toast.LENGTH_SHORT).show();
                break;
        }
        saveStorageConfiguration();
    }

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 181992;

    private boolean checkStoragePermission() {
        int hasReadExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWriteExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return hasReadExternalStoragePermission == PackageManager.PERMISSION_GRANTED && hasWriteExternalStoragePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed ")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SettingsActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean reading = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writing = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if(reading && writing) {
                    Toast.makeText(getApplicationContext(),"Permession Granted",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Permession Denied",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "You Denied Permession", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveStorageConfiguration();
        Log.d(TAG, "SettingsActivity onDestroy");
    }

    private static final String RADIO_BUTTON_STORAGE_TYPE_TEXT = "storage_type_text";
    private static final String RADIO_BUTTON_STORAGE_TYPE_ID = "storage_type_id";
    private static String storageTypeTextByRadioButton;
    private static int storageTypeIdByRadioButton;

    void saveStorageConfiguration() {
        SharedPreferences sPref = this.getSharedPreferences("configuration", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(RADIO_BUTTON_STORAGE_TYPE_TEXT, storageTypeTextByRadioButton);
        Log.d(TAG, "saveStorageConfiguration" + storageTypeTextByRadioButton);
        editor.putInt(RADIO_BUTTON_STORAGE_TYPE_ID, storageTypeIdByRadioButton);
        Log.d(TAG, RADIO_BUTTON_STORAGE_TYPE_ID + "saveStorageConfiguration" + storageTypeIdByRadioButton);
        editor.apply();
    }

    void loadStorageConfiguration() {
        SharedPreferences sPref = this.getSharedPreferences("configuration", MODE_PRIVATE);
        storageTypeIdByRadioButton = sPref.getInt(RADIO_BUTTON_STORAGE_TYPE_ID, -1);
        Log.d(TAG, RADIO_BUTTON_STORAGE_TYPE_ID + "loadStorageConfiguration storageTypeIdByRadioButton = " + storageTypeIdByRadioButton);
        if (storageTypeIdByRadioButton == -1) {
            Toast.makeText(getBaseContext(), "Memory storage", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton radioButton = radioGroup.findViewById(storageTypeIdByRadioButton);
        radioButton.setChecked(true);
    }

    // получаем текущего хранилище, выбор текст, или memory storage
    public static String getStorageTypeText(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("configuration", MODE_PRIVATE);
        String storageValue = sPref.getString(RADIO_BUTTON_STORAGE_TYPE_TEXT, "memory_storage");
        return storageValue;
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
        saveStorageConfiguration();
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