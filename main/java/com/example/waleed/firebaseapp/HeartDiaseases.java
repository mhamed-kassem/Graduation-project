package com.example.waleed.firebaseapp;

/**
 * Created by waleed on 6/18/2018.
 */

import android.net.Uri;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class HeartDiaseases extends AppCompatActivity
{

    public String result;
    URL url;
    TextView mlshow;
    EditText age,sex,cp,trestbps,chol,fbs,restecg,thalach,exang,oldpeak,slop,ca,thal;
    String Age,Sex,Cp,Trestbps,Chol,Fbs,Restecg,Thalach,Exang,Oldpeak,Slop,Ca,Thal;
    Button predictHeart,clear;

    private static final String BASE_URL = "http://0dfde958.ngrok.io/python/modelfinal.py";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                if (firstdigit == 1) {

                    mlshow.setText("you are sick by ratio " + digitsArray[3] + "" + digitsArray[4] + "" + digitsArray[5] + "" + digitsArray[6] +""+digitsArray[7]+ ""+ digitsArray[8]+" you must go to doctor");
                } else {
                    mlshow.setText(" you are healthy by ratio  " + digitsArray[3] + "" + digitsArray[4] + "" + digitsArray[5] + "" + digitsArray[6] + "" + digitsArray[7] + ""+ digitsArray[8]);
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
}