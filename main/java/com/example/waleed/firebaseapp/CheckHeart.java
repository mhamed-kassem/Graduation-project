package com.example.waleed.firebaseapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckHeart extends AppCompatActivity
{

    public String result;
    URL url;
    TextView mlshow;
    EditText age,sex,cp,trestbps,chol,fbs,restecg,thalach,exang,oldpeak,slop,ca,thal;
    String Age,Sex,Cp,Trestbps,Chol,Fbs,Restecg,Thalach,Exang,Oldpeak,Slop,Ca,Thal;
    Button predictHeart,clear;

    private static final String BASE_URL = "http://ddc9e081.ngrok.io/python/modelfinal4.py";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_heart);
        mlshow =(TextView)findViewById(R.id.ViewResult);
        age =(EditText)findViewById(R.id.Age);
        sex =(EditText)findViewById(R.id.sex);
        cp =(EditText)findViewById(R.id.Cb);
        trestbps =(EditText)findViewById(R.id.Trestbps);
        chol =(EditText)findViewById(R.id.chol);
        fbs =(EditText)findViewById(R.id.fbs);
        restecg =(EditText)findViewById(R.id.restecg);
        thalach =(EditText)findViewById(R.id.thalach);
        exang =(EditText)findViewById(R.id.exang);
        oldpeak =(EditText)findViewById(R.id.oldpeak);
        slop =(EditText)findViewById(R.id.slop);
        ca =(EditText)findViewById(R.id.ca);
        thal =(EditText)findViewById(R.id.thal);
        predictHeart=(Button)findViewById(R.id.predictHeart);
        clear=(Button)findViewById(R.id.clear);





        predictHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Age =age.getText().toString();
                Sex =sex.getText().toString();
                Cp =cp.getText().toString();
                Trestbps =trestbps.getText().toString();
                Chol = chol.getText().toString();
                Fbs = fbs.getText().toString();
                Restecg = restecg.getText().toString();
                Thalach = thalach.getText().toString();
                Exang = exang.getText().toString();
                Oldpeak = oldpeak.getText().toString();
                Slop = slop.getText().toString();
                Ca = ca.getText().toString();
                Thal = thal.getText().toString();

                //mlshow = (TextView) findViewById(R.id.mlresult);
                try {

                    Uri builtUri = Uri.parse(BASE_URL).buildUpon()

                            .appendQueryParameter("age",Age)
                            .appendQueryParameter("sex", Sex)
                            .appendQueryParameter("cp", Cp)
                            .appendQueryParameter("trestbps",Trestbps )
                            .appendQueryParameter("chol", Chol)
                            .appendQueryParameter("fbs", Fbs)
                            .appendQueryParameter("restecg",Restecg)
                            .appendQueryParameter("thalach",Thalach)
                            .appendQueryParameter("exang", Exang)
                            .appendQueryParameter("oldpeak", Oldpeak)
                            .appendQueryParameter("slop", Slop)
                            .appendQueryParameter("ca", Ca)
                            .appendQueryParameter("thal",Thal)

/*
                    .appendQueryParameter("age","67")
                    .appendQueryParameter("sex", "1")
                    .appendQueryParameter("cp", "4")
                    .appendQueryParameter("trestbps","160" )
                    .appendQueryParameter("chol", "286")
                    .appendQueryParameter("fbs", "0")
                    .appendQueryParameter("restecg","2")
                    .appendQueryParameter("thalach","108")
                    .appendQueryParameter("exang", "1")
                    .appendQueryParameter("oldpeak", "1.5")
                    .appendQueryParameter("slop", "2")
                    .appendQueryParameter("ca", "3")
                    .appendQueryParameter("thal","3")
                    */
                            .build();

                    new MyAsyncTaskgetNews().execute(builtUri.toString());
                } catch (Exception ex) {
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
            }
        });



    }

    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {


        }
        @Override
        protected String  doInBackground(String... params) {
            String NewsData="";
            try
            {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                NewsData=Stream2String(in);
                in.close();

                publishProgress(NewsData );

            } catch (Exception e) {
                // TODO Auto-generated catch block

            }
            return null;
        }
        protected void onProgressUpdate(final String... progress) {

            //mlshow = (TextView) findViewById(R.id.mlresult);
            try {
                String test = progress[0];
                String[] digitsArray = test.split("");

                String rresult = digitsArray[1];
                int firstdigit = Integer.parseInt(rresult);
                if (firstdigit == 0) {

                    mlshow.setText("depending on the number of vessels that are narrowed is equel = "+ firstdigit +" "+ digitsArray[3] + "" + digitsArray[4] + "" + digitsArray[5] + "" + digitsArray[6] +""+digitsArray[7]+ ""+ digitsArray[8]+" you are healthy");
                } else if(firstdigit==1){
                    mlshow.setText("depending on the number of vessels that are narrowed is equel = "+ firstdigit +" "+ digitsArray[14] + "" + digitsArray[15] + "" + digitsArray[16] + "" + digitsArray[17] + "" + digitsArray[18] + ""+ digitsArray[19]+" You may have a heart attack");
                }
                else if(firstdigit==2){
                    mlshow.setText(" depending on the number of vessels that are narrowed is equel = "+ firstdigit +" "+ digitsArray[25] + "" + digitsArray[26] + "" + digitsArray[27] + "" + digitsArray[28] + "" + digitsArray[29] + ""+ digitsArray[30]+" You may have a heart attack you must go to the doctor");
                }
                else if(firstdigit==3){
                    mlshow.setText(" depending on the number of vessels that are narrowed is equel = "+ firstdigit +" "+ digitsArray[36] + "" + digitsArray[37] + "" + digitsArray[38] + "" + digitsArray[39] + "" + digitsArray[40] + ""+ digitsArray[41]+" You may have a heart attack you must go to the doctor you are serious ");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
        protected void onPostExecute(String  result2){

        }


    }

    void clearAll()
    {
        age.setText("");
        sex.setText("");
        cp.setText("");
        trestbps.setText("");
        chol.setText("");
        fbs.setText("");
        restecg.setText("");
        thalach.setText("");
        exang.setText("");
        oldpeak.setText("");
        slop.setText("");
        ca.setText("");
        thal.setText("");
        mlshow.setText("");
    }


    public String Stream2String(InputStream inputStream) {
        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String Text="";
        try{
            while((line=bureader.readLine())!=null) {
                Text+=line;
            }
            inputStream.close();
        }catch (Exception ex){}
        return Text;
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

