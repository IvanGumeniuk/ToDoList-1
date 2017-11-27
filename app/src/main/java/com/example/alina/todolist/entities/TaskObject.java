package com.example.alina.todolist.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alina.todolist.enums.TaskState;

import java.util.UUID;

/**
 * Created by Alina on 02.11.2017.
 */

public abstract class TaskObject implements Parcelable {



    private String uuid;
    private String description;
    private TaskState status;

    public TaskObject() {
        status = TaskState.ALL;
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.description);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    protected TaskObject (Parcel in) {
        this.uuid = in.readString();
        this.description = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : TaskState.values()[tmpStatus];
    }

    public boolean isDone() {
        return status == TaskState.DONE;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getStatus() {
        return status;
    }

    public void setStatus(TaskState status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskObject that = (TaskObject) o;

        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TaskObject{" +
                "uuid='" + uuid + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
