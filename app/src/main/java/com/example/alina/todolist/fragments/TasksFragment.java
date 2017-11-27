package com.example.alina.todolist.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alina.todolist.CreateTaskActivity;
import com.example.alina.todolist.MainActivity;
import com.example.alina.todolist.R;
import com.example.alina.todolist.adapters.TaskAdapter;
import com.example.alina.todolist.data.IDataSource;
import com.example.alina.todolist.data.SharedPreferencesDataSource;
import com.example.alina.todolist.entities.Task;
import com.example.alina.todolist.enums.ActivityRequest;
import com.example.alina.todolist.enums.BundleKey;

import java.util.ArrayList;

import javax.sql.DataSource;

public class TasksFragment extends Fragment {

    private ArrayList<Task> tasks;
    private RecyclerView taskRecyclerView;
    private IDataSource dataSource;
    private TaskAdapter taskAdapter;

    public static TasksFragment newInstance(ArrayList<Task> tasks) {
        TasksFragment fragment = new TasksFragment();
        fragment.tasks = tasks;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        dataSource = new SharedPreferencesDataSource(getContext().getApplicationContext());
        taskRecyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        taskAdapter = new TaskAdapter(tasks, new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
                intent.putExtra(BundleKey.TASK.name(), task);
                startActivityForResult(intent, ActivityRequest.UPDATE_TASK.ordinal());
            }
        });
        taskRecyclerView.setAdapter(taskAdapter);
        taskRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (ActivityRequest.values()[requestCode]) {
            case UPDATE_TASK:
                if(resultCode == Activity.RESULT_OK) {
                    Task task = data.getParcelableExtra(BundleKey.TASK.name());
                    if (task != null) {
                        dataSource.updateTask(task, taskAdapter.getData().indexOf(task));
                        taskAdapter.update(task);
                    }
                }
                break;
        }
    }
}
