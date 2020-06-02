package com.example.familyplanner.InProcess;

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

public class InProcessAdapter extends ArrayAdapter<InProcessTask> {

    private ArrayList<InProcessTask> ipTasks;

    public InProcessAdapter(@NonNull Context context, ArrayList<InProcessTask> ipTasks) {
        super(context, R.layout.my_task_adapter_item, ipTasks);
        this.ipTasks = ipTasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        InProcessTask ipTask = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.in_process_item, null);
        }
        ((TextView) convertView.findViewById(R.id.ip_headline_tv)).setText(ipTask.getIp_task_topic());
        ((TextView) convertView.findViewById(R.id.ip_time_tv)).setText(ipTask.getIp_deadline());
        InProcessAdapter.this.notifyDataSetChanged();
        return convertView;
    }

}
