package com.example.waleed.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class test extends AppCompatActivity {

    Button Save;
    EditText lowPresuure,HighPressure;
    EditText Temp,HeartRate;
    Firebase fireRef;
    private SaveSharedPreference saveSharedPreference;
    private test context;
    private Menu menu_of_tab;

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        saveSharedPreference = new SaveSharedPreference();
        context = test.this;
        fireRef =new Firebase("https://fir-app-b2fa9.firebaseio.com/");

        Save=(Button)findViewById(R.id.SaveButton);
        lowPresuure=(EditText) findViewById(R.id.Lowpressure);
        HighPressure=(EditText)findViewById(R.id.Highpressure);
        Temp=(EditText) findViewById(R.id.Tembreture);
        HeartRate=(EditText) findViewById(R.id.HeartRate);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                final DataUser datauser =new DataUser();
            /*    String tem=Temp.getText().toString();
                String heart=HeartRate.getText().toString();
                String low =lowPresuure.getText().toString();
                String high =HighPressure.getText().toString();*/
                int heart =Integer.parseInt(HeartRate.getText().toString());
                int low =Integer.parseInt(lowPresuure.getText().toString());
                int high =Integer.parseInt(HighPressure.getText().toString());
                float tem=Float.parseFloat(Temp.getText().toString());


                final DataUser user=new DataUser(date ,saveSharedPreference.getEmail(context),heart,tem,low,high);

                final Firebase childUser=fireRef.child("DataPatient");
                final  Firebase childmailPatient =childUser.child(saveSharedPreference.getEmail(context)).child(date);
                childmailPatient.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        childmailPatient.setValue(user);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu_of_tab = menu;
        getMenuInflater().inflate(R.menu.menu_of_tab,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out){
            SaveSharedPreference.setEmail(context,"");
            startActivity(new Intent(context,LoginActivity.class));
            finish();
        }
        if (item.getItemId() == R.id.profile){
            startActivity(new Intent(context,ProfileActivity.class));
            finish();
        }

        if (item.getItemId() == R.id.home){
            startActivity(new Intent(context,HomeActivityList.class));
            finish();
        }

        if (item.getItemId() == R.id.bluetooth){
            startActivity(new Intent(context,PairedDevice.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
