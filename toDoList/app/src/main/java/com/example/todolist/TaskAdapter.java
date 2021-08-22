package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private LayoutInflater inflater;
    private int layout;
    private List<Task> taskList;

    public TaskAdapter(@NonNull Context context, int resource, List<Task> taskList) {
        super(context, resource, taskList);
        this.taskList = taskList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Task task = taskList.get(position);
        viewHolder.description.setText(task.getDescription());
        viewHolder.title.setText(task.getTitle());
        return convertView;
    }

    private class ViewHolder {
        TextView title, description;
        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.task_title);
            description = (TextView) view.findViewById(R.id.task_description);
        }
    }
}
