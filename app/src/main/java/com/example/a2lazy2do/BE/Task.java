package com.example.a2lazy2do.BE;

import java.io.Serializable;
import java.util.Date;
import com.google.firebase.firestore.ServerTimestamp;

public class Task implements Serializable {

    private String taskId , taskName , createdBy;

    @ServerTimestamp
    private Date timestamp;

    public Task() {
    }

    public Task(String taskId, String taskName, String createdBy) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.createdBy = createdBy;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
