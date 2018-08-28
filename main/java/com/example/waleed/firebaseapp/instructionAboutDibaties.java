package com.example.waleed.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class instructionAboutDibaties extends AppCompatActivity {

    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_about_dibaties);


        view =(TextView)findViewById(R.id.textArea_informationDiabates);
        view.setMovementMethod(new ScrollingMovementMethod());
        view.setText("\t\t\tDIABATES INSTRUCTIONS\n" +
                "\n" +
                "\n" +
                "1- [Pregnancies]: You will enter the number of times pregnant.\n" +
                "\n" +
                "2- [Glucose]: Plasma glucose concentration a 2 hours \n" +
                "in an oral glucose tolerance test.\n" +
                "\n" +
                "3- [BloodPressure]: Diastolic blood pressure (the lower number).\n" +
                "\n" +
                "4- [SkinThickness]: (Triceps skin fold thickness) \n" +
                "a value used to estimate \n" +
                "body fat, measured on the right arm halfway \n" +
                "between the olecranon process \n" +
                "of the elbow and the acromial process of the scapula,\n" +
                " normal thickness i\n" +
                "n males is 12 mm; in females, 23 mm.\n" +
                "\n" +
                "5- [Insulin]: 2-Hour serum insulin.\n" +
                "\n" +
                "6- [BMI]:Body mass index, expressed in weight in kg/(height in m)^2.\n" +
                "\n" +
                "7- [DiabetesPedigreeFunction]:family history of diabates.\n" +
                "\n" +
                "8- [Age]: Enter your age expressed in years.");
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Menu menu_of_tab = menu;
        getMenuInflater().inflate(R.menu.menu_of_tab,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.log_out){

            startActivity(new Intent(getBaseContext(),LoginActivity.class));
            finish();
        }
        if (item.getItemId() == R.id.home){
            startActivity(new Intent(getBaseContext(),HomeActivityList.class));
        }

        if (item.getItemId() == R.id.stethoscope){
            startActivity(new Intent(getBaseContext(),selectDisease.class));
            finish();
        }

        if (item.getItemId() == R.id.profile){
            startActivity(new Intent(getBaseContext(),ProfileActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
