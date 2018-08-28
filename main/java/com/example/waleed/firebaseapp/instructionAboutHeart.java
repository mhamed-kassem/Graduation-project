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

public class instructionAboutHeart extends AppCompatActivity {

    TextView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_about_heart);

        view =(TextView)findViewById(R.id.textArea_information);
        view.setMovementMethod(new ScrollingMovementMethod());
        view.setText("\t     \t\t\t  HEART INSTUCTIONS\n" +
                "\n" +
                "1- [Age] : You will simply enter you age in numbers.\n" +
                "\n" +
                "2- [Sex] : You will enter your sex (0 or 1) 0 for female and 1 for male.\n" +
                "\n" +
                "3- [cp(chest pain type)]: you will enter your chest pain type in numbers\n" +
                "1 for typical angina: occurs when the heart has to work harder than normal, during exercise, for example. \n" +
                "2 for atypical angina: it can just as easily occur during exercise as it can while you are resting.\n" +
                "3 for nonanginal pain: its duration is over 30 minutes or less than 5 seconds, it increases with inspiration, can be brought on with one movement of the trunk or arm, can be brought on by local fingers pressure.\n" +
                "4 for asymptomatic: there is no symptoms.\n" +
                " \n" +
                "4- [trestbps]: Resting systolic blood pressure (the upper number).\n" +
                "\n" +
                "5- [chol]: Serum cholesterol in numbers.\n" +
                "\n" +
                "6- [fbs]: Is your fasting blood sugar is over 120  (yes or no), enter 1 for yes and 0 for no.\n" +
                "\n" +
                "7- [restecg(resting electrocardiogram result)]:  resting electrocardiogram is a test that measures the electrical activity of the heart, \n" +
                "you will take the test and check the result and you will enter \n" +
                "0 if its normal\n" +
                "1 if having ST-T wave abnormality \n" +
                "2 if  having LV hypertrophy\n" +
                "\n" +
                "8- [thalach]: you will enter the Maximum heart rate achieved in numbers.\n" +
                "\n" +
                "9- [exang]: Do you Exercise induced angina? you will enter\n" +
                "1 if yes\n" +
                "0 if no\n" +
                "(Angina): Angina is a type of chest pain that results from reduced blood flow to the heart. \n" +
                "\n" +
                "10- [oldpeak]: depending on the restecg(no.7) you will check the ST depression  induced by exercise relative to rest,\n" +
                "you will enter the measurement in numbers.\n" +
                "\n" +
                "11- [slope]: Again you will check the restecg(no.7) to check The slope of the peak exercise ST segment,\n" +
                "and you will enter \n" +
                "1 if upsloping\n" +
                "2 if flat\n" +
                "\n" +
                "12- [ca]: you will make a fluoroscopy detection, (Fluoroscopy) is a type of medical imaging that shows a continuous X-ray image on a monitor,\n" +
                "and you will check the number of vessels colored by fluoroscopy then you will enter the number.\n" +
                "\n" +
                "13- [thal (Exercise thallium scintigraphic defects)]: A thallium stress test is a form of (Scintigraphy)is a diagnostic test you will make, and you will check the results, \n" +
                "And you will enter\n" +
                "3 if its normal\n" +
                "6 if its fixed defect\n" +
                "7 if its reversible defect\n" +
                "\n");
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
