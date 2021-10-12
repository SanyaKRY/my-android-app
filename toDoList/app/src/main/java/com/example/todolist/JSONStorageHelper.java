package com.example.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class JSONStorageHelper {

    private static final String INTERNAL_STORAGE_ALL = "internal_storage_JSON_all";
    private static final String INTERNAL_STORAGE_FAVOURITE = "internal_storage_JSON_favourite";

    private static final String EXTERNAL_STORAGE_ALL = "external_storage_JSON_all";
    private static final String EXTERNAL_STORAGE_FAVOURITE = "external_storage_JSON_favourite";

    private static final String SHARED_PREFERENCES_STORAGE_ALL = "shared_preferences_storage_JSON_all";
    private static final String SHARED_PREFERENCES_STORAGE_FAVOURITE = "shared_preferences_storage_JSON_favourite";

    static void exportJSONToInternalStorage(Context context, List<Task> taskList, String name) {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                context.openFileOutput(name, MODE_PRIVATE)))) {
            bw.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void exportJSONToSharedPreferences(Context context, List<Task> taskList, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_preferences_storage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(name, json);
        editor.apply();
    }

    static ArrayList<Task> importJSONFromSharedPreferencesAll(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_preferences_storage", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SHARED_PREFERENCES_STORAGE_ALL, null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> taskList = gson.fromJson(json, type);
        if (taskList == null) {
            Log.d(TAG, "FileNotFoundException importJSONFromSharedPreferencesAll");
            taskList = getDefaultTaskListSharedPref();
        }
        return taskList;
    }

    static ArrayList<Task> importJSONFromSharedPreferencesFavourite(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_preferences_storage", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SHARED_PREFERENCES_STORAGE_FAVOURITE, null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> taskList = gson.fromJson(json, type);
        if (taskList == null) {
            Log.d(TAG, "FileNotFoundException importJSONFromSharedPreferencesAll");
            taskList = getDefaultFavouriteTaskListSharedPref();
        }
        return taskList;
    }

    static ArrayList<Task> importJSONFromInternalStorageAll(Context context) {
                Log.d(TAG, "importJSONFromInternalStorageAll");
                ArrayList<Task> taskList = null;
                try(BufferedReader br = new BufferedReader(new InputStreamReader(
                        context.openFileInput(INTERNAL_STORAGE_ALL)))) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Task>>() {}.getType();
                    StringBuilder content = new StringBuilder();
            final String LINE_SEP = System.getProperty("line.separator");
            boolean addLineSep = true;
            String lineStr = br.readLine();
            while (lineStr != null) {
                content.append(lineStr);
                lineStr = br.readLine();
                addLineSep = (lineStr != null ? true : false);
                if (addLineSep) {
                    content.append(LINE_SEP);
                }
            }
            taskList = gson.fromJson(content.toString(), type);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException importJSONFromInternalStorageAll");
            taskList = getDefaultTaskListInternal();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    static ArrayList<Task> importJSONFromInternalStorageFavourite(Context context) {
        ArrayList<Task> taskList = null;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(
                context.openFileInput(INTERNAL_STORAGE_FAVOURITE)))) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>() {}.getType();
            StringBuilder content = new StringBuilder();
            final String LINE_SEP = System.getProperty("line.separator");
            boolean addLineSep = true;
            String lineStr = br.readLine();
            while (lineStr != null) {
                content.append(lineStr);
                lineStr = br.readLine();
                addLineSep = (lineStr != null ? true : false);
                if (addLineSep) {
                    content.append(LINE_SEP);
                }
            }
            taskList = gson.fromJson(content.toString(), type);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException importJSONFromInternalStorageAll");
            taskList = getDefaultFavouriteTaskListInternal();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    static void saveAllList(Context context, ArrayList<Task> taskList) {
        String storageTypeValue = SettingsActivity.getStorageTypeText(context);
        if (storageTypeValue == "memory_storage") {
            return;
        } else {
            switch(storageTypeValue) {
                case "Shared Pref":
                    exportJSONToSharedPreferences(context, taskList, SHARED_PREFERENCES_STORAGE_ALL);
                    break;
                case "Internal":
                    exportJSONToInternalStorage(context, taskList, INTERNAL_STORAGE_ALL);
                    break;
                case "External":
                    exportJSONToExternalStorage(context, taskList, EXTERNAL_STORAGE_ALL);
                    break;
                case "SQL":
                    DatabaseAdapter adapter = new DatabaseAdapter(new DatabaseHelper(context));
                    adapter.open();
                    adapter.deleteAndAddListTasks(taskList, "tasks");
                    adapter.close();
                    break;
            }
        }
    }

    static void exportJSONToExternalStorage(Context context, List<Task> taskList, String name) {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(context.getExternalFilesDir("to-do-list"), name)))) {
            bw.write(json);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    static ArrayList<Task> importJSONFromExternalStorageAll(Context context) {
        Log.d(TAG, "importJSONFromExternalStorageAll");
        ArrayList<Task> taskList = null;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(context.getExternalFilesDir("to-do-list"), EXTERNAL_STORAGE_ALL)))) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>() {}.getType();
            StringBuilder content = new StringBuilder();
            final String LINE_SEP = System.getProperty("line.separator");
            boolean addLineSep = true;
            String lineStr = br.readLine();
            while (lineStr != null) {
                content.append(lineStr);
                lineStr = br.readLine();
                addLineSep = (lineStr != null ? true : false);
                if (addLineSep) {
                    content.append(LINE_SEP);
                }
            }
            taskList = gson.fromJson(content.toString(), type);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException importJSONFromInternalStorageAll");
            taskList = getDefaultTaskListExternal();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    static ArrayList<Task> importJSONFromExternalStorageFavourite(Context context) {
        Log.d(TAG, "importJSONFromExternalStorageAll");
        ArrayList<Task> taskList = null;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(context.getExternalFilesDir("to-do-list"), EXTERNAL_STORAGE_FAVOURITE)))) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>() {}.getType();
            StringBuilder content = new StringBuilder();
            final String LINE_SEP = System.getProperty("line.separator");
            boolean addLineSep = true;
            String lineStr = br.readLine();
            while (lineStr != null) {
                content.append(lineStr);
                lineStr = br.readLine();
                addLineSep = (lineStr != null ? true : false);
                if (addLineSep) {
                    content.append(LINE_SEP);
                }
            }
            taskList = gson.fromJson(content.toString(), type);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException importJSONFromInternalStorageAll");
            taskList = getDefaultFavouriteTaskListExternal();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }


    static void saveFavouriteList(Context context, ArrayList<Task> taskList) {
        String storageTypeValue = SettingsActivity.getStorageTypeText(context);
        if (storageTypeValue == "memory_storage") {
            return;
        } else {
            switch(storageTypeValue) {
                case "Shared Pref":
                    exportJSONToSharedPreferences(context, taskList, SHARED_PREFERENCES_STORAGE_FAVOURITE);
                    break;
                case "Internal":
                    exportJSONToInternalStorage(context, taskList, INTERNAL_STORAGE_FAVOURITE);
                    break;
                case "External":
                    exportJSONToExternalStorage(context, taskList, EXTERNAL_STORAGE_FAVOURITE);
                    break;
                case "SQL":
                    DatabaseAdapter adapter = new DatabaseAdapter(new SQLiteOpenHelperFavourite(context));
                    adapter.open();
                    adapter.deleteAndAddListTasks(taskList, "tasksFavourite");
                    adapter.close();
                    break;
            }
        }
    }

    static ArrayList<Task> getDefaultTaskListMemory() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("Memory Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        taskListAll.add(new Task("Memory Покормить кошку", "Кошку можно кормить натуральными продуктами, но имейте в виду, что это не должна быть еда со стола. Можно давать нежирные кисломолочные продукты (творог, кефир), мясные субпродукты (печень, легкое, почки, сердце), мясо (говядину, баранину, крольчатину), рыбу (сельдь, сардины, скумбрию), овощи (кабачки, тыкву, огурцы)."));
        taskListAll.add(new Task("Memory Придумать имя кошке", "Есть один действенный способ как можно назвать кошку. Для этого нужно выбрать любую букву алфавита, и на неё придумать кличку, учитывая пол животного. Затем следует позвать кота придуманным именем."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultTaskListSharedPref() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("SharedPref Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        taskListAll.add(new Task("SharedPref Покормить кошку", "Кошку можно кормить натуральными продуктами, но имейте в виду, что это не должна быть еда со стола. Можно давать нежирные кисломолочные продукты (творог, кефир), мясные субпродукты (печень, легкое, почки, сердце), мясо (говядину, баранину, крольчатину), рыбу (сельдь, сардины, скумбрию), овощи (кабачки, тыкву, огурцы)."));
        taskListAll.add(new Task("SharedPref Придумать имя кошке", "Есть один действенный способ как можно назвать кошку. Для этого нужно выбрать любую букву алфавита, и на неё придумать кличку, учитывая пол животного. Затем следует позвать кота придуманным именем."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultTaskListExternal() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("External Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        taskListAll.add(new Task("External Покормить кошку", "Кошку можно кормить натуральными продуктами, но имейте в виду, что это не должна быть еда со стола. Можно давать нежирные кисломолочные продукты (творог, кефир), мясные субпродукты (печень, легкое, почки, сердце), мясо (говядину, баранину, крольчатину), рыбу (сельдь, сардины, скумбрию), овощи (кабачки, тыкву, огурцы)."));
        taskListAll.add(new Task("External Придумать имя кошке", "Есть один действенный способ как можно назвать кошку. Для этого нужно выбрать любую букву алфавита, и на неё придумать кличку, учитывая пол животного. Затем следует позвать кота придуманным именем."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultFavouriteTaskListExternal() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("External Favourite Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultTaskListInternal() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("Internal Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        taskListAll.add(new Task("Internal Покормить кошку", "Кошку можно кормить натуральными продуктами, но имейте в виду, что это не должна быть еда со стола. Можно давать нежирные кисломолочные продукты (творог, кефир), мясные субпродукты (печень, легкое, почки, сердце), мясо (говядину, баранину, крольчатину), рыбу (сельдь, сардины, скумбрию), овощи (кабачки, тыкву, огурцы)."));
        taskListAll.add(new Task("Internal Придумать имя кошке", "Есть один действенный способ как можно назвать кошку. Для этого нужно выбрать любую букву алфавита, и на неё придумать кличку, учитывая пол животного. Затем следует позвать кота придуманным именем."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultFavouriteTaskListMemory() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("Memory Favourite Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultFavouriteTaskListSharedPref() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("SharedPref Favourite Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        return taskListAll;
    }

    static ArrayList<Task> getDefaultFavouriteTaskListInternal() {
        ArrayList taskListAll = new ArrayList<>();
        taskListAll.add(new Task("Internal Favourite Купить кошку", "Прежде всего, кошка является символом домашнего уюта, считает зоопсихолог, доцент Московского психолого-педагогического института Мария Сотская."));
        return taskListAll;
    }
}
