package com.example.familyplanner.InProcess;

public class InProcessTask {
    private String ip_num_task;
    private String ip_task_topic;
    private String ip_task_content;
    private String ip_deadline;

    public String getIp_task_topic() {
        return ip_task_topic;
    }

    public String getIp_task_content() {
        return ip_task_content;
    }

    public String getIp_deadline() {
        return ip_deadline;
    }

    public String getIp_num_task() {
        return ip_num_task;
    }

    public InProcessTask(String ip_task_topic, String ip_task_content, String ip_deadline, String ip_num_task) {
        this.ip_task_topic = ip_task_topic;
        this.ip_task_content = ip_task_content;
        this.ip_deadline = ip_deadline;
        this.ip_num_task = ip_num_task;
    }
}
