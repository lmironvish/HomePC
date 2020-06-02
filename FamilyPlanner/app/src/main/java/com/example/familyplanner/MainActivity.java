package com.example.familyplanner;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.familyplanner.FamilyTask.FamilyTaskFragment;
import com.example.familyplanner.InProcess.InProcessFragment;
import com.example.familyplanner.MyTask.MyTaskFragment;
import com.example.familyplanner.R;

public class MainActivity extends FragmentActivity {

    private ImageButton my_task_button, family_task_button, in_process_button;

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

        ft.show(familyTaskFragment);
        ft.hide(inProcessFragment);
        ft.hide(myTaskFragment);

        ft.commit();


        my_task_button = findViewById(R.id.btn1);
        my_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                myTaskFragment.update();

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

                familyTaskFragment.update();

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

                inProcessFragment.update();

                ft.hide(familyTaskFragment);
                ft.hide(myTaskFragment);
                ft.show(inProcessFragment);

                ft.commit();
            }
        });


    }
}

