package com.example.familyplanner.MyTask;

public class MyTask {
    private String task_id;
    private String task_topic;
    private String task_content;
    private String deadline;

    public String getTask_topic() {
        return task_topic;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getTask_content() {
        return task_content;
    }

    public String getTask_id() {
        return task_id;
    }

    public MyTask(String task_topic, String task_content, String deadline, String task_id) {
        this.task_topic = task_topic;
        this.deadline = deadline;
        this.task_content = task_content;
        this.task_id = task_id;
    }
}
