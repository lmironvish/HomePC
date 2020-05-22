package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainTasks extends AppCompatActivity {

    private EditText editText;
    private Button btn_add;
    private ListView task_list;
    private ArrayList<Task> tasks;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_main);


        editText = (EditText) findViewById(R.id.et1);
        btn_add = (Button) findViewById(R.id.btn1);
        task_list = (ListView) findViewById(R.id.student_list);

        tasks = new ArrayList<>();

        taskAdapter = new TaskAdapter(this, tasks);
        task_list.setAdapter(taskAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task student = new Task(editText.getText().toString().split(" ")[0],
                        editText.getText().toString().split(" ")[1]);
                tasks.add(student);

                taskAdapter.notifyDataSetChanged();
            }
        });
    }
}