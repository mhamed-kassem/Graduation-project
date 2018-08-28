package com.example.waleed.firebaseapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sara Alkurdy on 02/25/2018.
 */

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profile_photo;
    private Context context;
    private PreferenceImage preferenceImage;
    private SaveSharedPreference saveSharedPreference;
    private Menu menu_of_tab;
    private NetworkAvailable networkAvailable;
    private boolean isNetworkConnected;
    private TextView email_cover;
    private TextView name_cover;


    private EditText Birthday, Number, the_gender, email;
    private String Gender;


    //public RadioButton Male, Female;


    // private TextView name;
    private Firebase firRef;
    private Firebase firRefChild;
    private User user;
    private String get_email;
    private String get_name;
    private String get_email_with_dot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        VIEWS();
    }


    private void VIEWS() {
        context = ProfileActivity.this;
        preferenceImage = new PreferenceImage(context);
        saveSharedPreference = new SaveSharedPreference();
        profile_photo = (CircleImageView) findViewById(R.id.profile_photo);

        name_cover = (TextView) findViewById(R.id.name_cover);
        email_cover = (TextView) findViewById(R.id.email_cover);
        //  name = (TextView)findViewById(R.id.the_name);
        email = (EditText) findViewById(R.id.the_email);

        Birthday = (EditText) findViewById(R.id.the_birthday);
        Number = (EditText) findViewById(R.id.the_number);

        //  Male = (RadioButton) findViewById(R.id.radio_Male);
        //  Female = (RadioButton) findViewById(R.id.radio_Female);
        the_gender = (EditText) findViewById(R.id.the_gender);

        get_email = saveSharedPreference.getEmail(context);


        firRef = new Firebase("https://fir-app-b2fa9.firebaseio.com/");
        firRefChild = firRef.child("User");
        final User user = new User();


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                get_name = dataSnapshot.child(get_email).child("name").getValue(String.class);
                Number.setText(dataSnapshot.child(get_email).child("number").getValue(String.class));
                Birthday.setText(dataSnapshot.child(get_email).child("birthday").getValue(String.class));
                the_gender.setText(dataSnapshot.child(get_email).child("gender").getValue(String.class) == "Male" ? "Male" : "Female");

                name_cover.setText(get_name);
                // name.setText(get_name);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        firRefChild.addValueEventListener(postListener);


        get_email_with_dot = get_email.replace(",", ".");
        email.setText(get_email_with_dot);
        email_cover.setText(get_email_with_dot);


        preferenceImage.Show_Saved_Image(context, profile_photo);

        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceImage.AddPhoto(context);
            }
        });

        Birthday.setOnClickListener(new View.OnClickListener() { // هنا الحدث الذي يقوم بجلب التاريخ من dialog ويحفظه في edit text
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Birthday.setText(i + "-" + i1 + "-" + i2);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                        //      pickerDialog.setTitle("Select Date");
                        .show();

            }
        });

        networkAvailable = new NetworkAvailable(ProfileActivity.this);
        isNetworkConnected = networkAvailable.isNetworkAvailable();
        if (!isNetworkConnected) {
            Toast.makeText(context, "Please Connect Your Device With Internet", Toast.LENGTH_LONG).show();
        }
    }

    /*********  CALL METHOD TO SAVE & ADD NEW IMAGE VIEW SHAREDPREFRENCES  USING BASE64 ENCODE ********/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            preferenceImage.DeletePhoto(context, profile_photo);
            preferenceImage.Save_Image_In_Preferences(context, profile_photo, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu_of_tab = menu;
        getMenuInflater().inflate(R.menu.menu_of_tab, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            saveSharedPreference.setEmail(context, "");
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
        if (item.getItemId() == R.id.home) {
            startActivity(new Intent(context, HomeActivityList.class));
            finish();
        }

        if (item.getItemId() == R.id.stethoscope) {
            startActivity(new Intent(context, selectDisease.class));
         //   finish();
        }

        if (item.getItemId() == R.id.bluetooth) {
            startActivity(new Intent(context, PairedDevice.class));
          //  finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void ClickMale(View view) {
        this.Gender = "Male";
    }

    public void ClickFemale(View view) {
        this.Gender = "Female";
    }



}
