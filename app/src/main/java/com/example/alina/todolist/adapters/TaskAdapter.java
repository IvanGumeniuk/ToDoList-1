package com.example.alina.todolist.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alina.todolist.R;
import com.example.alina.todolist.entities.Task;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Alina on 14.11.2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(@NonNull List<Task> tasks) {
        super();
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.TaskViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return (tasks == null) ? 0 : tasks.size();
    }

    public void add(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        TextView date;

        public TaskViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameTextView);
            description = (TextView) itemView.findViewById(R.id.descriptionTextView);
            date = (TextView) itemView.findViewById(R.id.dateTextView);
        }
        public void bind(Task task) {
            name.setText(task.getName());
            description.setText(task.getDescription());
            date.setText(task.getExpireDateString());

        }
    }
}
