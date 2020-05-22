package com.example.fragmenttest.InProcess;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.fragmenttest.FamilyTask.FamilyTask;

public class InProcessAdapter extends ArrayAdapter<FamilyTask> {

    public InProcessAdapter(@NonNull Context context, int resource) {
        super(context, resource);


    }
}
