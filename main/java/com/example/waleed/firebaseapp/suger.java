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

public class suger extends AppCompatActivity {

    public String result;
    URL url;
    TextView mlshow;
    EditText Pregnancies,Glucose,BloodPressure,SkinThickness,Insulin,BMI,DiabetesPedigreeFunction,Age;
    String   pregnancies,glucose,bloodPressure,skinThickness,insulin,bMI,diabetesPedigreeFunction,age;
    Button predictHeart,clear;
    private static final String BASE_URL = "http://ddc9e081.ngrok.io/python/modelfinal3.py";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suger);

        mlshow =(TextView)findViewById(R.id.ViewResultSuger);
        Pregnancies =(EditText)findViewById(R.id.Pregnancies);
        Glucose =(EditText)findViewById(R.id.Glucose);
        BloodPressure =(EditText)findViewById(R.id.BloodPressure);
        SkinThickness =(EditText)findViewById(R.id.SkinThickess);
        Insulin =(EditText)findViewById(R.id.Insulin);
        BMI =(EditText)findViewById(R.id.BMI);
        DiabetesPedigreeFunction =(EditText)findViewById(R.id.DiabetesPedigreeFunction);
        Age =(EditText)findViewById(R.id.AAge);

        predictHeart=(Button)findViewById(R.id.predictHeartSuger);
        clear=(Button)findViewById(R.id.clearSuger);

        predictHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pregnancies =Pregnancies.getText().toString();
                glucose =Glucose.getText().toString();
                bloodPressure =BloodPressure.getText().toString();
                skinThickness =SkinThickness.getText().toString();
                insulin = Insulin.getText().toString();
                bMI = BMI.getText().toString();
                diabetesPedigreeFunction = DiabetesPedigreeFunction.getText().toString();
                age = Age.getText().toString();


                //mlshow = (TextView) findViewById(R.id.mlresult);
                try {

                    Uri builtUri = Uri.parse(BASE_URL).buildUpon()

                            .appendQueryParameter("Pregnancies",pregnancies)
                            .appendQueryParameter("Glucose", glucose)
                            .appendQueryParameter("BloodPressure", bloodPressure)
                            .appendQueryParameter("SkinThickness",skinThickness )
                            .appendQueryParameter("Insulin", insulin)
                            .appendQueryParameter("BMI", bMI)
                            .appendQueryParameter("DiabetesPedigreeFunction",diabetesPedigreeFunction)
                            .appendQueryParameter("Age",age)


                            .build();

                    new MyAsyncTaskgetNews().execute(builtUri.toString());
                } catch (Exception ex) {
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mlshow.setText("waleed mohamed");
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

                    mlshow.setText("you are have Diabaties by ratio " + digitsArray[3] + "" + digitsArray[4] + "" + digitsArray[5] + "" + digitsArray[6] +""+digitsArray[7]+ ""+ digitsArray[8]+" you must go to doctor");
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
        Pregnancies.setText("");
        Glucose.setText("");
        BloodPressure.setText("");
        SkinThickness.setText("");
        Insulin.setText("");
        BMI.setText("");
        DiabetesPedigreeFunction.setText("");
        Age.setText("");
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
