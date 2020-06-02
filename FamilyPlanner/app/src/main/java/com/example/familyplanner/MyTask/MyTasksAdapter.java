package com.example.familyplanner.MyTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.familyplanner.R;

import java.util.ArrayList;

public class MyTasksAdapter extends ArrayAdapter<MyTask> {

    private ArrayList<MyTask> myTasks;

    public MyTasksAdapter(@NonNull Context context, ArrayList<MyTask> myTasks) {
        super(context, R.layout.my_task_adapter_item, myTasks);
        this.myTasks = myTasks;
    }

    @NonNull
    @Override
    public View getView(final int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        MyTask myTask = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.my_task_adapter_item, null);
        }
        ((TextView) convertView.findViewById(R.id.my_headline_tv)).setText(myTask.getTask_topic());
        ((TextView) convertView.findViewById(R.id.my_time_tv)).setText(myTask.getDeadline());
        MyTasksAdapter.this.notifyDataSetChanged();
        return convertView;
    }
}
