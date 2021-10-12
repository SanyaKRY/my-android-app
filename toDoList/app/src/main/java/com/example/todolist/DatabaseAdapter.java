package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAdapter {
    private CustomSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(CustomSQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {dbHelper.getColumnTitle(), dbHelper.getColumnDescription()};
        return  database.query(dbHelper.getTableName(), columns, null, null, null, null, null);
    }

    public ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnTitle()));
            String description = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnDescription()));
            tasks.add(new Task(title, description));
        }
        cursor.close();
        return  tasks;
    }

    public void deleteAndAddListTasks(ArrayList<Task> listTasks, String table) {
        database.execSQL("delete from "+ table);
        ContentValues cv;
        for (Task task : listTasks) {
            cv = new ContentValues();
            cv.put(dbHelper.getColumnTitle(), task.getTitle());
            cv.put(dbHelper.getColumnDescription(), task.getDescription());
            database.insert(dbHelper.getTableName(), null, cv);
        }
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, dbHelper.getTableName());
    }
}
