package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import java.util.List;

public class AllTaskAdapter extends ArrayAdapter<Task> {

    private static final String TAG = "TAG";

    private Context context;
    private int layout;
    private List<Task> taskList;

    public AllTaskAdapter(@NonNull Context context, int resource, List<Task> taskList) {
        super(context, resource, taskList);
        this.taskList = taskList;
        this.layout = resource;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Task task = taskList.get(position);
        viewHolder.description.setText(task.getDescription());
        viewHolder.title.setText(task.getTitle());

        viewHolder.popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.popupMenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_all_tasks, popupMenu.getMenu());
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.nav_change_task:
                                        Toast.makeText(context,
                                                "Change task",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, NewTaskActivity.class);
                                        intent.putExtra(Task.class.getSimpleName(), task);
                                        intent.putExtra("adapterName", "all");
                                        intent.putExtra("position", position);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        context.startActivity(intent);
                                        return true;
                                    case R.id.nav_delete_task:
                                        Toast.makeText(context,
                                                "Delete task",
                                                Toast.LENGTH_SHORT).show();
                                        remove(task);
                                        return true;
                                    case R.id.nav_add_task_to_favorites:
                                        MainActivity.favouriteTaskAdapter.add(task);
                                        remove(task);
                                        Toast.makeText(context,
                                                "Add a task to favorites",
                                                Toast.LENGTH_SHORT).show();
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });

                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        Log.d(TAG, "Dismiss");
                    }
                });
                popupMenu.show();
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, task.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, NewTaskActivity.class);
                intent.putExtra(Task.class.getSimpleName(), task);
                intent.putExtra("adapterName", "all");
                intent.putExtra("position", position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public void remove(@Nullable Task object) {
        super.remove(object);
        this.notifyDataSetChanged();
    }


    private class ViewHolder {
        TextView title, description;
        ImageButton popupMenu;
        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.task_title);
            description = (TextView) view.findViewById(R.id.task_description);
            popupMenu = (ImageButton) view.findViewById(R.id.threePointImageButton);
        }
    }

}
