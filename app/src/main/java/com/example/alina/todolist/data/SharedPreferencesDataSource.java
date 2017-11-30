package com.example.alina.todolist.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.alina.todolist.entities.Task;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Alina on 14.11.2017.
 */

public class SharedPreferencesDataSource extends JsonParser implements IDataSource {

    private static final String PREF_NAME = "SharedPrefDataSource";
    private static final String KEY_TASK = "KEY_TASK";

    private SharedPreferences preferences;
    private Type taskListType;
    private ArrayList<Task> taskList;

    public SharedPreferencesDataSource(Context context){
        if (context != null) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            if (!isTaskListEmpty()){
                taskList = convertJsonToListData(preferences.getString(KEY_TASK, ""), taskListType);
            } else {
                taskList = new ArrayList<>();
            }
        }
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    @Override
    public boolean createTask(@NonNull Task task) {
        boolean result;
        result = taskList.add(task);
        preferences.edit().putString(KEY_TASK, convertDataListToJson(taskList, taskListType)).apply();
        return result;
    }

    @Override
    public boolean updateTask(@NonNull Task task, @IntRange(from = 0,
            to = Integer.MAX_VALUE) int index) {
        boolean result = false;

        if (index >= 0 && index < taskList.size()) {
            taskList.set(index, task);
            preferences.edit().putString(KEY_TASK, convertDataListToJson(taskList, taskListType)).apply();
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateTask(@NonNull Task task) {
        int position = 0;
        for (Task x : taskList){
            if (x.equals(task))
                position = taskList.indexOf(x);
        }
        return updateTask(task, position);
    }

    private boolean isTaskListEmpty(){
        return TextUtils.isEmpty(preferences.getString(KEY_TASK, ""));
    }

}
