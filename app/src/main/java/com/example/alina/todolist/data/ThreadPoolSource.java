package com.example.alina.todolist.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.alina.todolist.entities.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Leonid on 28.11.2017.
 */

public class ThreadPoolSource extends JsonParser implements IDataSource {
    private static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();
    private static final String FILE_NAME = "data.txt";

    private ThreadPoolExecutor executor;
    private ThreadCallback callback;
    private ArrayList<Task> taskList = new ArrayList<>();
    private Context context;

    public interface ThreadCallback{
        void onReadFileFinish(ArrayList<Task> taskList);
        void onWriteFileFinish();
    }

    public ThreadPoolSource(Context context){
        this.context = context;
        this.callback = (ThreadCallback) context;
        executor = new ThreadPoolExecutor(
                CORE_COUNT,
                CORE_COUNT,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>()
        );
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return null;
    }

    @Override
    public boolean createTask(@NonNull Task task) {
        return false;
    }

    @Override
    public boolean updateTask(@NonNull Task task, int index) {
        return false;
    }

    @Override
    public boolean updateTask(@NonNull Task task) {
        return false;
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() {
        String data = "";

        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                data = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
