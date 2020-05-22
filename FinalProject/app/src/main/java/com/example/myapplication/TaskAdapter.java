package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class TaskAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;

    public TaskAdapter(@NonNull Context context, ArrayList<Task> tasks) {
        super(context, R.layout.task_adapter_item, tasks);
        this.tasks = tasks;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Task task = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_adapter_item, null);
        }

        ((TextView) convertView.findViewById(R.id.tv_number)).setText(String.valueOf(position+1));
        ((TextView) convertView.findViewById(R.id.tv_name1)).setText(task.getTask());
        ((TextView) convertView.findViewById(R.id.tv_name2)).setText(task.getDeadline());

        ((Button) convertView.findViewById(R.id.btn_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.remove(position);
                TaskAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
