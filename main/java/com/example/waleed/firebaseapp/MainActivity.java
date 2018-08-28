package com.example.waleed.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class MainActivity extends AppCompatActivity {
    Firebase fireRef;
   // ListView list;
   // ArrayList<String>Users=new ArrayList<>();
    EditText id,Mail,password;
    Button Signup ,SignIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireRef =new Firebase("https://fir-app-b2fa9.firebaseio.com/");
        id=(EditText)findViewById(R.id.EditId);
        Mail=(EditText)findViewById(R.id.EditMail);
        password=(EditText)findViewById(R.id.EditPassword);
        Signup=(Button) findViewById(R.id.BtnId);
        SignIN=(Button)findViewById(R.id.BtnSingInMain);

Signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String Id=id.getText().toString();
        String mail=Mail.getText().toString();
        String Password=password.getText().toString();
        final User use=new User(Id,mail,Password);
        final Firebase childUser=fireRef.child("User");


        childUser.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                {
                    if(dataSnapshot.child(use.getMail()).exists())
                    {
                        Toast.makeText(MainActivity.this,"user is already exit",Toast.LENGTH_SHORT).show();
                    }
                    else{

                        childUser.child(use.getMail()).setValue(use);
                        Toast.makeText(MainActivity.this,"user added",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



/*
        Firebase child= fireRef.child("User");
        Firebase childChild=child.child("Patien");
        childChild.child("Id").setValue(IDcas);
        childChild.child("Mail").setValue(Name);
        childChild.child("Password").setValue(Geender);
*/
    }
});
        SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });




        /*
        final ArrayAdapter<String>ArrayAdaptor=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Users);

        list.setAdapter(ArrayAdaptor);
        fireRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String values=dataSnapshot.getValue(String.class);
                Users.add(values);
                ArrayAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String values=dataSnapshot.getValue(String.class);
                Users.add(values);
                ArrayAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key=ky.getText().toString();
                String Value= value.getText().toString();

                Firebase fire=fireRef.child(key);
                fire.setValue(Value);

            }
        });
*/
    }




    /*
    void ADDartist()
    {

        String Id=id.getText().toString();
        String Name=name.getText().toString();
        String Geender=gender.getText().toString();
        if(!TextUtils.isEmpty(Id)&&!TextUtils.isEmpty(Name)&&!TextUtils.isEmpty(Geender) )
        {
            Artist art= new Artist(Id,Name,Geender);
            Data.child(Id).setValue(art);
        }
        else{

            Toast.makeText(this,"enter all field",Toast.LENGTH_LONG).show();
        }

    }*/
}
