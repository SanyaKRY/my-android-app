package com.example.todolist2;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InternalStorage implements Storage {

    private static final String INTERNAL_STORAGE = "internal_storage.json";
    private static InternalStorage instance;

    Context applicationContext;

    private InternalStorage(Context applicationContext) {
        this.applicationContext = applicationContext;;
    }

    public static InternalStorage getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new InternalStorage(applicationContext);
        }
        return instance;
    }

    @Override
    public void writeInStorage(ArrayList<Task> taskList) {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                applicationContext.openFileOutput(INTERNAL_STORAGE, MODE_PRIVATE)))) {
            bw.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> readFromStorage() {
        ArrayList<Task> taskList = null;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(
                applicationContext.openFileInput(INTERNAL_STORAGE)))) {
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
