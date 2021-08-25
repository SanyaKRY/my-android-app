package com.example.todolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    TextView title;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "NewTaskActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);

        Bundle arguments = getIntent().getExtras();
        Task task;
        if(arguments!=null){
            task = arguments.getParcelable(Task.class.getSimpleName());
            title.setText(task.getTitle());
            description.setText(task.getDescription());
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_save:
                Toast.makeText(NewTaskActivity.this, "Save",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "NewTaskActivity onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "NewTaskActivity onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "NewTaskActivity onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "NewTaskActivity onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "NewTaskActivity onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "NewTaskActivity onRestart");
    }
}