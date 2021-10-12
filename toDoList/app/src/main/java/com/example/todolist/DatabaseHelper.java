package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DatabaseHelper extends CustomSQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "tasks"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (" +  COLUMN_TITLE
                + " TEXT, " + COLUMN_DESCRIPTION + " TEXT);");
        db.execSQL("CREATE TABLE tasksFavourite (" +  COLUMN_TITLE
                + " TEXT, " + COLUMN_DESCRIPTION + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_TITLE
                + ", " + COLUMN_DESCRIPTION  + ") VALUES ('SQL Купить кошку', 'Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская.');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_TITLE
                + ", " + COLUMN_DESCRIPTION  + ") VALUES ('SQL Покормить кошку', 'Кошку можно кормить натуральными продуктами, но имейте в виду, что это не должна быть еда со стола. Можно давать нежирные кисломолочные продукты (творог, кефир), мясные субпродукты (печень, легкое, почки, сердце), мясо (говядину, баранину, крольчатину), рыбу (сельдь, сардины, скумбрию), овощи (кабачки, тыкву, огурцы).');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_TITLE
                + ", " + COLUMN_DESCRIPTION  + ") VALUES ('SQL Придумать имя кошке', 'Есть один действенный способ как можно назвать кошку. Для этого нужно выбрать любую букву алфавита, и на неё придумать кличку, учитывая пол животного. Затем следует позвать кота придуманным именем.');");
        db.execSQL("INSERT INTO tasksFavourite (" + COLUMN_TITLE
                + ", " + COLUMN_DESCRIPTION  + ") VALUES ('SQL Favourite Купить кошку', 'Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская.');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    @Override
    public String getTableName() {
        return "tasks";
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
