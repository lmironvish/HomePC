package com.example.myapplication;

public class Task {
    private String task;
    private String deadline;

    public Task(String task, String deadline) {
        this.task = task;
        this.deadline = deadline;
    }

    public String getTask() {
        return task;
    }

    public String getDeadline() {
        return deadline;
    }

}