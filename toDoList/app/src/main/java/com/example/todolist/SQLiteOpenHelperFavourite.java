package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class SQLiteOpenHelperFavourite extends CustomSQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "tasksFavourite"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";

    public SQLiteOpenHelperFavourite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasksFavourite (" +  COLUMN_TITLE
                + " TEXT, " + COLUMN_DESCRIPTION + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_TITLE
                + ", " + COLUMN_DESCRIPTION  + ") VALUES ('SQL Favourite Купить кошку', 'Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская.');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    @Override
    public String getTableName() {
        return "tasksFavourite";
    }

    @Override
    public String getColumnTitle() {
        return "title";
    }

    @Override
    public String getColumnDescription() {
        return "description";
    }
}
