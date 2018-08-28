package com.example.waleed.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class selectDisease extends AppCompatActivity {
    Button Suger,Heart,instructionHeart,instructionDiabates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_disease);
        Suger=(Button)findViewById(R.id.sugerButton);
        Heart=(Button)findViewById(R.id.heartdisease);
        instructionHeart=(Button)findViewById(R.id.instructionHeart);
        instructionDiabates=(Button)findViewById(R.id.insturectionDiabaties);

        Suger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(selectDisease.this,suger.class);
                startActivity(intent);
            }
        });
        Heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(selectDisease.this,CheckHeart.class);
                startActivity(intent);
            }
        });
        instructionHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(selectDisease.this,instructionAboutHeart.class);
                startActivity(intent);
            }
        });
        instructionDiabates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(selectDisease.this,instructionAboutDibaties.class);
                startActivity(intent);
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



        if (item.getItemId() == R.id.bluetooth) {
            startActivity(new Intent(getBaseContext(), PairedDevice.class));
            // finish();
        }

        if (item.getItemId() == R.id.profile){
            startActivity(new Intent(getBaseContext(),ProfileActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

}
