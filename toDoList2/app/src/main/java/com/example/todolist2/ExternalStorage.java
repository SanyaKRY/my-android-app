package com.example.todolist2;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExternalStorage implements Storage {

    private static final String EXTERNAL_STORAGE = "external_storage.json";
    private static final String EXTERNAL_STORAGE_DIR = "toDoListExternalStorage";
    private static ExternalStorage instance;

    Context applicationContext;

    private ExternalStorage(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static ExternalStorage getInstance(Context context) {
        if (instance == null) {
            instance = new ExternalStorage(context);
        }
        return instance;
    }

    @Override
    public void writeInStorage(ArrayList<Task> taskList) {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
                applicationContext.getExternalFilesDir(EXTERNAL_STORAGE_DIR), EXTERNAL_STORAGE)))) {
            bw.write(json);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> readFromStorage() {
        ArrayList<Task> taskList = null;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(applicationContext.getExternalFilesDir(EXTERNAL_STORAGE_DIR), EXTERNAL_STORAGE)))) {
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

}
