package com.example.waleed.firebaseapp;


import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class login extends AppCompatActivity {

    Firebase fireRef;
    EditText Email,Passwordd;
    Button SignIn;

    public static  String EmailLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fireRef =new Firebase("https://fir-app-b2fa9.firebaseio.com/User");
        Email=(EditText)findViewById(R.id.input_email);
        Passwordd=(EditText)findViewById(R.id.input_password);
        SignIn=(Button) findViewById(R.id.btn_login);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mail=Email.getText().toString();
                String Password=Passwordd.getText().toString();
                singin(Mail,Password);
                EmailLogin=Email.getText().toString();

            }
        });

          }

    public void singin( final String mail, final  String password) {

        fireRef.addValueEventListener(new com.firebase.client.ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                int id=1;
                {
                    if(dataSnapshot.child(mail).exists()) {
                        if (!mail.isEmpty()) {
                            User chilvalue = dataSnapshot.child(mail).getValue(User.class);
                            if (chilvalue.getPassword().equals(password)) {
/*    */
                                Intent intent = new Intent(login.this, test.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(login.this, "password wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    else{

                        Toast.makeText(login.this,"must Regisre user",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



}
