package com.example.fragmenttest.FamilyTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fragmenttest.R;

import java.util.ArrayList;

public class FamilyTaskAdapter extends ArrayAdapter<FamilyTask> {

    private ArrayList<FamilyTask> familyTasks;

    public FamilyTaskAdapter(@NonNull Context context, ArrayList<FamilyTask> familyTasks) {
        super(context, R.layout.fam_task_adapter_item, familyTasks);
        this.familyTasks = familyTasks;
    }

    @NonNull
    @Override
    public View getView(final int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        FamilyTask familyTask = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fam_task_adapter_item, null);
        }
        ((TextView) convertView.findViewById(R.id.fam_headline_tv)).setText(familyTask.getFam_task_topic());
        ((TextView) convertView.findViewById(R.id.fam_time_tv)).setText(familyTask.getFam_deadline());
        FamilyTaskAdapter.this.notifyDataSetChanged();
        return convertView;
    }
}
