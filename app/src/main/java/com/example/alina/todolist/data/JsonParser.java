package com.example.alina.todolist.data;

import com.example.alina.todolist.entities.Task;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 28.11.2017.
 */

abstract class JsonParser {
    private GsonBuilder gsonBuilder;

    JsonParser(){
        gsonBuilder = new GsonBuilder();
    }

    String convertDataListToJson(List<Task> task, Type type){
        return gsonBuilder.create().toJson(task, type);
    }

    <T> ArrayList<T> convertJsonToListData(String json, Type type){
        return gsonBuilder.create().fromJson(json, type);
    }
}
