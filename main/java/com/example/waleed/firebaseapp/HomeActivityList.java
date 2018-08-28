package com.example.waleed.firebaseapp;

/**
 * Created by Sara Alkurdy on 01-Mar-18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class HomeActivityList extends AppCompatActivity {

    private Button logout_btn;
    private Context context;
    private Menu menu_of_tab = null;
    private SaveSharedPreference save_shared_preference;

    private CircleImageView profile_photo;
    private PreferenceImage preferenceImage;
    private Firebase fireRef;

    private String time_user;
    private int low_pressure_user;
    private int high_pressure_user;
    private int heart_rate;
    private ArrayList<Integer> arrayUserLowPressure;
    private ArrayList<Integer> arrayUserHighPressure;
    private ArrayList<String> arrayUserTime;
    private ArrayList<Integer> arrayUserHeartRate;
    private ArrayList<Float> arrayUserTemperature;
    private float temperature_user;
    private Firebase fireRefChildren;
    private String Email;
    private NetworkAvailable networkAvailable;
    private boolean isNetworkConnected;
    private Firebase firRef_getName;
    private Firebase firRefChildGetName;
    private String get_email;
    private String get_name;
    private TextView high_pressure_post;
    private TextView low_pressure_post;
    private TextView temperature_post;
    private TextView heartRate_post;
    private TextView post_time;
    private TextView person_name_post;
    private Button delete_btn_post;
    private PostAdapter postAdapter;
    private ListView list_of_posts;
    private ArrayList<Integer> arrayUserLowPressureReversed;
    private ArrayList<Integer> arrayUserHighPressureReversed;
    private ArrayList<String> arrayUserTimeReversed;
    private ArrayList<Integer> arrayUserHeartRateReversed;
    private ArrayList<Float> arrayUserTemperatureReversed;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        context = HomeActivityList.this;
        save_shared_preference = new SaveSharedPreference();
        Email = save_shared_preference.getEmail(context); //Email = sara@gmail,com  ///
        VIEW();
    }


    private void VIEW() {

        list_of_posts = (ListView) findViewById(R.id.list_of_posts);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorAccent));
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new Task().execute();
            }
        });


        preferenceImage = new PreferenceImage(context);


        networkAvailable = new NetworkAvailable(HomeActivityList.this);
        isNetworkConnected = networkAvailable.isNetworkAvailable();
        if (!isNetworkConnected) {
            Toast.makeText(context, "Please Connect Your Device With Internet", Toast.LENGTH_LONG).show();
        }


        if (Email.length() <= 0) {
            // call Login Activity
            startActivity(new Intent(HomeActivityList.this, LoginActivity.class));
            finish();
        }else GET_DATA_FIREBASE(); //-----


    }


    private void GET_DATA_FIREBASE() {


        firRef_getName = new Firebase("https://fir-app-b2fa9.firebaseio.com/");
        firRefChildGetName = firRef_getName.child("User");
        get_email = save_shared_preference.getEmail(context); //sara@gmail,com
        final User user = new User();


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                get_name = dataSnapshot.child(get_email).child("name").getValue(String.class); //sara
                save_shared_preference.setName(context, get_name);//sara

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        firRefChildGetName.addValueEventListener(postListener);


        fireRef = new Firebase("https://fir-app-b2fa9.firebaseio.com/DataPatient");
        fireRefChildren = fireRef.child(Email);//sara@gmail,com
        fireRefChildren.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayUserLowPressure = new ArrayList<>();
                arrayUserHighPressure = new ArrayList<>();
                arrayUserTime = new ArrayList<>();
                arrayUserHeartRate = new ArrayList<>();
                arrayUserTemperature = new ArrayList<>();


                arrayUserLowPressureReversed = new ArrayList<>();
                arrayUserHighPressureReversed = new ArrayList<>();
                arrayUserTimeReversed = new ArrayList<>();
                arrayUserHeartRateReversed = new ArrayList<>();
                arrayUserTemperatureReversed = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    DataUser data_user = postSnapshot.getValue(DataUser.class);///////////-----
                    //DataUser data_user = new DataUser();

                    time_user = data_user.getTime();
                    arrayUserTime.add(time_user);  // "Wed, 4 Apr 2018, 03:01" , "Wed, 4 Apr 2018, 03:13"
                    //Collections.reverse(arrayUserTime);//"Wed, 4 Apr 2018, 03:13"  , "Wed, 4 Apr 2018, 03:01"

                    low_pressure_user = data_user.getLow_prusser();
                    arrayUserLowPressure.add(low_pressure_user); // 80 , 100
                    //Collections.reverse(arrayUserLowPressure); // 100 , 80 ////////////////////usedddddddd

                    high_pressure_user = data_user.getHigh_pressure();
                    arrayUserHighPressure.add(high_pressure_user); //120  , 140
                   // Collections.reverse(arrayUserHighPressure); // 140 , 120

                    heart_rate = data_user.getHeartRate();
                    arrayUserHeartRate.add(heart_rate); //80 , 85
                    //Collections.reverse(arrayUserHeartRate); //85 , 80

                    temperature_user = data_user.getTempreture();
                    arrayUserTemperature.add(temperature_user); //37 , 36
                    //Collections.reverse(arrayUserTemperature); //36 , 37
                }

                for (int j=arrayUserHeartRate.size()-1 ; j>=0 ; j--){
                    arrayUserHeartRateReversed.add(arrayUserHeartRate.get(j));
                    arrayUserHighPressureReversed.add(arrayUserHighPressure.get(j));
                    arrayUserLowPressureReversed.add(arrayUserLowPressure.get(j));
                    arrayUserTimeReversed.add(arrayUserTime.get(j));
                    arrayUserTemperatureReversed.add(arrayUserTemperature.get(j));
                }


                postAdapter = new PostAdapter(HomeActivityList.this, android.R.layout.simple_list_item_1, arrayUserHighPressure);
                list_of_posts.setAdapter(postAdapter);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(HomeActivityList.this, "An Error Occurred" + firebaseError.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

    }

    public int getItemCount() {
        return (arrayUserHighPressure == null) ? 0 : arrayUserHighPressure.size();
    }


    private class PostAdapter extends ArrayAdapter {
        public PostAdapter(@NonNull Context context, int resource, ArrayList<Integer> arrayUserHighPressure) {
            super(context, resource, arrayUserHighPressure);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.post_item, parent, false);

            high_pressure_post = (TextView) view.findViewById(R.id.high_pressure);
            low_pressure_post = (TextView) view.findViewById(R.id.low_pressure);
            temperature_post = (TextView) view.findViewById(R.id.temperature);
            heartRate_post = (TextView) view.findViewById(R.id.heart_rate);
            post_time = (TextView) view.findViewById(R.id.post_time);
            profile_photo = (CircleImageView) view.findViewById(R.id.profile_photo);
            person_name_post = (TextView) view.findViewById(R.id.person_name);
            delete_btn_post = (Button) view.findViewById(R.id.delete_btn);

            preferenceImage = new PreferenceImage(HomeActivityList.this);
            high_pressure_post.setText(arrayUserHighPressureReversed.get(position) + "");
            low_pressure_post.setText(arrayUserLowPressureReversed.get(position) + "");
            temperature_post.setText(arrayUserTemperatureReversed.get(position) + "");
            post_time.setText(arrayUserTimeReversed.get(position));
            person_name_post.setText(save_shared_preference.getName(HomeActivityList.this));
            heartRate_post.setText(arrayUserHeartRateReversed.get(position) + "");
            preferenceImage.Show_Saved_Image(HomeActivityList.this, profile_photo);

            delete_btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int positio_of_post = position;

                    final String get_post_time = arrayUserTime.get(position); //post_time.getText().toString();

                    Toast.makeText(HomeActivityList.this, get_post_time, Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(HomeActivityList.this)
                            .setTitle("Warning")
                            .setMessage("Do you really want to delete this post ?")
                            .setIcon(R.drawable.warning2)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    fireRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            if (dataSnapshot.child(Email).child(get_post_time).exists()) {

                                                fireRef.child(Email).child(get_post_time).removeValue();

                                                Toast.makeText(HomeActivityList.this, "This Post Deleted Successfully", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });

            postAdapter.notifyDataSetChanged();


            return view;


        }
    }


    class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            MyAinm.fade(new MyAinm.ClickAction() {

                @Override
                public void click() {
                    GET_DATA_FIREBASE();
                    list_of_posts.setAlpha(1f);
                }
            },list_of_posts,1000,1f,0);
            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
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
            save_shared_preference.setEmail(context, ""); //// length = 0
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
        if (item.getItemId() == R.id.profile) {
            startActivity(new Intent(context, ProfileActivity.class));
          //  finish();
        }

        if (item.getItemId() == R.id.stethoscope) {
            startActivity(new Intent(context, selectDisease.class));
          //  finish();
        }

        if (item.getItemId() == R.id.bluetooth) {
            startActivity(new Intent(context, PairedDevice.class));
           // finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("Exit")
                .setMessage("Do you want to exit ?")
                .setIcon(R.drawable.exit)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }
                )
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}
