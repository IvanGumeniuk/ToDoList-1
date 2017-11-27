package com.example.alina.todolist.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.alina.todolist.entities.Task;
import com.example.alina.todolist.enums.TaskState;
import com.example.alina.todolist.fragments.TasksFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alina on 27.11.2017.
 */

public class TasksFragmentPagerAdapter extends FragmentPagerAdapter {

    private HashMap<TaskState, ArrayList<Task>> splitTasks = new HashMap<>();

    private Context context;

    public TasksFragmentPagerAdapter(Context context, FragmentManager fragmentManager,
                                     ArrayList<Task> tasks) {
        super(fragmentManager);
        this.context = context;
        splitTasksByStatus(tasks);
    }

    private void splitTasksByStatus(ArrayList<Task> tasks) {
        for (int i = 0; i < TaskState.values().length; i++) {
            splitTasks.put(TaskState.values()[i], new ArrayList<Task>());
        }
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            Log.d("log", task.toString());
            if (task.getStatus() != null) {
                splitTasks.get(task.getStatus()).add(task);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return TasksFragment.newInstance(splitTasks.get(TaskState.values()[position]));
    }

    @Override
    public int getCount() {
        return TaskState.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(TaskState.values()[position].pageTitle);
    }
}
