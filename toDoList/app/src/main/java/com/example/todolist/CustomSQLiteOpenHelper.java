package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class CustomSQLiteOpenHelper extends SQLiteOpenHelper  {
    public CustomSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    abstract public String getTableName();

    abstract public String getColumnTitle();

    abstract public String getColumnDescription();
}
