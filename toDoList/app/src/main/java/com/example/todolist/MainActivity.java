package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final int ZERO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAllTasks();
        setFavouriteTasks();
        setTab();
        setNavigationMenu();
    }

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private void setNavigationMenu() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView)findViewById(R.id.navigation_View);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                if (id == R.id.nav_tasks) {
                    Toast.makeText(MainActivity.this, "Tasks",Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Back",Toast.LENGTH_SHORT).show();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setTab() {
        Log.d(TAG, "MainActivity setTab()");
        TabHost tabHost = (TabHost) this.findViewById(android.R.id.tabhost);
        tabHost.setup();
        // LayoutInflater – это класс, который умеет из содержимого layout-файла создать View-элемент.
        // Метод который это делает называется inflate. Есть несколько реализаций этого метода с различными
        // параметрами. Но все они используют друг друга и результат их выполнения один – View.
        //TODO Разобрать, почему не получается воспользовать ся конструкией
        // View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.all_tasks_header, <ViewGroup_root> , false);
        // , что необходимо предать в <ViewGroup_root> , т.к. пытаясь передать id на уровень выше, чем TabHost, родительский для него, получаю ошибку
        // где в моей логике ошибка:?

        View allTasksHeaderView = LayoutInflater.from(MainActivity.this).inflate(R.layout.all_tasks_header, null);
        View favouriteTasksHeaderView = LayoutInflater.from(MainActivity.this).inflate(R.layout.favourite_tasks_header, null);
        tabHost.addTab(tabHost.newTabSpec("tag_all").setContent(R.id.tab_all).setIndicator(allTasksHeaderView));
        tabHost.addTab(tabHost.newTabSpec("tag_favourite").setContent(R.id.tab_favourite).setIndicator(favouriteTasksHeaderView));

        setCurrentTab(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                String tagName = null;
                switch (tabId) {
                    case "tag_all":
                        tagName = "All";
                        break;
                    case "tag_favourite":
                        tagName = "Favourite";
                        break;
                }
                Toast.makeText(getBaseContext(), tagName + " was pressed", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.TRANSPARENT);
                }
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FF00FF"));
            }
        });
    }

    private void setCurrentTab(TabHost tabHost) {
        tabHost.setCurrentTab(ZERO);
        tabHost.getTabWidget().getChildTabViewAt(ZERO).setBackgroundColor(Color.parseColor("#FF00FF"));
    }

    private void setAllTasks() {
        Log.d(TAG, "MainActivity setAllTasks()");
        ArrayList<Task> taskListAll = new ArrayList();
        taskListAll.add(new Task("Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        taskListAll.add(new Task("Покормить кошку", "Кошку можно кормить натуральными продуктами, но имейте в виду, что это не должна быть еда со стола. Можно давать нежирные кисломолочные продукты (творог, кефир), мясные субпродукты (печень, легкое, почки, сердце), мясо (говядину, баранину, крольчатину), рыбу (сельдь, сардины, скумбрию), овощи (кабачки, тыкву, огурцы)."));
        taskListAll.add(new Task("Придумать имя кошке", "Есть один действенный способ как можно назвать кошку. Для этого нужно выбрать любую букву алфавита, и на неё придумать кличку, учитывая пол животного. Затем следует позвать кота придуманным именем."));
        ListView allTaskListView = (ListView) findViewById(R.id.all_tasks);
        TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.task_list, taskListAll);
        allTaskListView.setAdapter(taskAdapter);
    }

    private void setFavouriteTasks() {
        Log.d(TAG, "MainActivity setFavouriteTasks()");
        ArrayList<Task> taskListFavourite = new ArrayList();
        taskListFavourite.add(new Task("Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        ListView favouriteTaskListView = (ListView) findViewById(R.id.favourite_tasks);
        TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.task_list, taskListFavourite);
        favouriteTaskListView.setAdapter(taskAdapter);
    }

    public void addTask(View view) {
        Log.d(TAG, "MainActivity addTask()");
        Toast.makeText(getBaseContext(), "Add button was pressed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "MainActivity onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "MainActivity onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "MainActivity onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "MainActivity onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "MainActivity onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "MainActivity onRestart");
    }
}