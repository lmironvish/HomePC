package com.example.fragmenttest;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fragmenttest.FamilyTask.FamilyTaskFragment;
import com.example.fragmenttest.InProcess.InProcessFragment;
import com.example.fragmenttest.MyTask.MyTaskFragment;

public class MainActivity extends FragmentActivity {

    private Button my_task_button, family_task_button, in_process_button;

    private MyTaskFragment myTaskFragment;
    private FamilyTaskFragment familyTaskFragment;
    private InProcessFragment inProcessFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTaskFragment = (MyTaskFragment) getSupportFragmentManager().findFragmentById(R.id.frA);
        familyTaskFragment = (FamilyTaskFragment) getSupportFragmentManager().findFragmentById(R.id.frB);
        inProcessFragment = (InProcessFragment) getSupportFragmentManager().findFragmentById(R.id.frC);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.hide(familyTaskFragment);
        ft.hide(inProcessFragment);
        ft.show(myTaskFragment);

        ft.commit();



        my_task_button = findViewById(R.id.btn1);
        my_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

               ft.hide(familyTaskFragment);
               ft.hide(inProcessFragment);
               ft.show(myTaskFragment);

               ft.commit();



            }
        });


        family_task_button = findViewById(R.id.btn2);
        family_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.hide(myTaskFragment);
                ft.hide(inProcessFragment);
                ft.show(familyTaskFragment);

                ft.commit();
            }
        });

        in_process_button = findViewById(R.id.btn3);
        in_process_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.hide(familyTaskFragment);
                ft.hide(myTaskFragment);
                ft.show(inProcessFragment);

                ft.commit();
            }
        });


    }
}
