package com.example.alina.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alina.todolist.adapters.SubTaskAdapter;
import com.example.alina.todolist.entities.SubTask;
import com.example.alina.todolist.entities.Task;
import com.example.alina.todolist.entities.TaskObject;
import com.example.alina.todolist.enums.BundleKey;
import com.example.alina.todolist.enums.TaskState;
import com.example.alina.todolist.fragments.DatePickerFragment;
import com.example.alina.todolist.fragments.AddSubTaskDialogFragment;
import com.example.alina.todolist.validators.Validator;

import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity implements
        DatePickerFragment.OnDateSelectedListener, AddSubTaskDialogFragment.CreateSubTaskDialogListener{

    private Task task;
    private TextInputLayout nameWrapper;
    private TextInputLayout descriptionWrapper;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private TextView dateTextView;
    private RecyclerView subTaskRecycler;
    private SubTaskAdapter subTaskAdapter;
    private LinearLayout taskDateLayout;
    private Menu menu;
    private Validator stringValidator = new Validator.StringValidatorBuilder()
            .setNotEmpty()
            .setMinLength(3)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null
                && bundle.containsKey(BundleKey.TASK.name())) {
            task = bundle.getParcelable(BundleKey.TASK.name());
            initUI();
            setData();
        } else {
            Toast.makeText(getApplicationContext(), "Task not found", Toast.LENGTH_LONG).show();
            finish();
        }
        initDatePickerClick();
        initSubTaskRecycler();
        initCreateTaskButton();
    }

    private void initUI() {
        nameWrapper = (TextInputLayout) findViewById(R.id.nameWrapper);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        descriptionWrapper = (TextInputLayout) findViewById(R.id.descriptionWrapper);
        descriptionEditText = (EditText) findViewById(R.id.descriptionText);
        subTaskRecycler = (RecyclerView) findViewById(R.id.subTaskRecycler);
        taskDateLayout = (LinearLayout) findViewById(R.id.taskDateLayout);
    }

    private void setData() {
        nameEditText.setText(task.getName());
        descriptionEditText.setText(task.getDescription());
        dateTextView.setText(task.getExpireDateString());
    }

    private void fillData() {
        task.setName(nameEditText.getText().toString());
        task.setDescription(descriptionEditText.getText().toString());
    }

    private void showEditDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddSubTaskDialogFragment subTaskDialogFragment = AddSubTaskDialogFragment.newInstance("SubTask dialog");
        subTaskDialogFragment.show(fragmentManager, fragmentManager.getClass().getSimpleName());
    }

    private void initDatePickerClick(){
        taskDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    @Override
    public void onFinishSubTask(SubTask subTask) {
        subTaskAdapter.addNewSubTask(subTask);
    }

    private void initSubTaskRecycler(){
        subTaskAdapter = new SubTaskAdapter();
        subTaskRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        subTaskRecycler.setAdapter(subTaskAdapter);
        if (task.getSubTasks().size() != 0){
            subTaskAdapter.addAllSubTask(task.getSubTasks());
        }
    }

    private void initCreateTaskButton() {
        FloatingActionButton createSubTaskButton = (FloatingActionButton)
                findViewById(R.id.createSubTaskButton);
        createSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_editor, menu);
        MenuItem doneItem = menu.findItem(R.id.item_done_task);
        MenuItem saveItem = menu.findItem(R.id.item_save);
        if (task.isDone()) {
            doneItem.setTitle(getString(R.string.undone_task));
            saveItem.setEnabled(false);
        }
        if (!task.isDone()) {
            doneItem.setTitle(getString(R.string.done_task));
            saveItem.setEnabled(true);
        }
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                saveTask();
                return true;
            case R.id.item_done_task:
                setTaskDone();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTaskDone() {
        if (task.getStatus() == TaskState.DONE) {
            task.setStatus(TaskState.ALL);
            saveTask();
        }
        if (task.isAllSubTasksDone() && task.getStatus() == TaskState.ALL) {
            task.setStatus(TaskState.DONE);
            saveTask();
        } else {
            Toast.makeText(this, "All SubTasks must be done!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTask() {
        if (validate(nameWrapper) && validate(descriptionWrapper)) {
            fillData();
            task.setSubTasks(subTaskAdapter.getSubTaskList());
            Intent result = new Intent();
            result.putExtra(BundleKey.TASK.name(), task);
            setResult(Activity.RESULT_OK, result);
            finish();
        }
    }

    private boolean validate(TextInputLayout wrapper) {
        wrapper.setErrorEnabled(false);
        boolean result = stringValidator.validate(wrapper.getEditText().getText().toString(),
                wrapper.getHint().toString());
        if (!result) {
            wrapper.setErrorEnabled(true);
            wrapper.setError(stringValidator.getLastMessage());
        }
        return result;
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSelected(Date date) {
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        task.setExpireDate(date);
        dateTextView.setText(task.getExpireDateString());
    }
}