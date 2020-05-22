package com.example.fragmenttest.FamilyTask;

public class FamilyTask {
    private String fam_task_num;
    private String fam_task_topic;
    private String fam_task_content;
    private String fam_deadline;


    public FamilyTask(String fam_task_topic, String fam_task_content, String fam_deadline, String fam_task_num) {
        this.fam_task_topic = fam_task_topic;
        this.fam_task_content = fam_task_content;
        this.fam_deadline = fam_deadline;
        this.fam_task_num = fam_task_num;
    }

    public String getFam_task_topic() {
        return fam_task_topic;
    }

    public String getFam_task_content() {
        return fam_task_content;
    }

    public String getFam_deadline() {
        return fam_deadline;
    }

    public String getFam_task_num() {
        return fam_task_num;
    }
}


