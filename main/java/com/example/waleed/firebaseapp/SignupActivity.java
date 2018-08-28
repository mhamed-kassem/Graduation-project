package com.example.waleed.firebaseapp;

/**
 * Created by Sara Alkurdy on 01-Mar-18.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name)
    EditText name_user;
    @BindView(R.id.input_email)
    EditText email_user;
    @BindView(R.id.input_password)
    EditText password_user;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;

    private String Gender;
    private String birthday;
    private String number;
    private String Key;
    //-----------------------------
    @BindView(R.id.input_Number)
    EditText Number_user;
    @BindView(R.id.input_Birthday)
    EditText Birthday_user;
    @BindView(R.id.input_key)
    EditText Key_user;
    //*******************************
    private String password;
    private String email;
    private String name;
    //-------------------------
    private Firebase fireRef;
    private SaveSharedPreference saveSharedPreference;
    private SignupActivity context;
    private NetworkAvailable networkAvailable;
    private boolean isNetworkConnected;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        VIEWS();
    }


    private void VIEWS() {
        fireRef = new Firebase("https://fir-app-b2fa9.firebaseio.com/");
        saveSharedPreference = new SaveSharedPreference();
        context = SignupActivity.this;

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        networkAvailable = new NetworkAvailable(SignupActivity.this);
        isNetworkConnected = networkAvailable.isNetworkAvailable();

    }




    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        if (!isNetworkConnected) {
            Toast.makeText(context,"Please Connect Your Device With Internet",Toast.LENGTH_LONG).show();
        }

        name = name_user.getText().toString();
        email = email_user.getText().toString();
        password = password_user.getText().toString();
        //---------------------------------------------
        //gender = password_user.getText().toString();

        birthday = Birthday_user.getText().toString();
        number = Number_user.getText().toString();
        Key = Key_user.getText().toString();

        /**** to accept email to firebase*/
        email= EncodeString(email);

        final User use = new User(name, email, password,number,birthday,this.Gender,Key);
        final Firebase childUser = fireRef.child("User");
        //to add data to firebase
        childUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //chech if  the same user is found on firebase

                    if (dataSnapshot.child(use.getMail()).exists()) {
                        email_user.setError("This email is already exist");
                        //Toast.makeText(SignupActivity.this, "user is already exit", Toast.LENGTH_SHORT).show();
                    } else {
                        //add the user
                        childUser.child(use.getMail()).setValue(use);
                        saveSharedPreference.setName(context, name_user.getText().toString());
                        saveSharedPreference.setEmail(context,email);
                        startActivity(new Intent(context,HomeActivityList.class));
                        Toast.makeText(SignupActivity.this, "user added", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }


    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }



    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup Failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = name_user.getText().toString();
        String email = email_user.getText().toString();
        String password = password_user.getText().toString();

        String  number = Number_user.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            name_user.setError("at least 3 characters");
            valid = false;
        } else {
            name_user.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_user.setError("enter a valid email address");
            valid = false;
        } else {
            email_user.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_user.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_user.setError(null);
        }

        if (number.isEmpty() || number.length() < 11 || number.length() > 11) {
            Number_user.setError("enter a valid number.");
            valid = false;
        } else {
            Number_user.setError(null);
        }


        return valid;
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

    public void ClickDate(View view) {
        final EditText editText= (EditText) view;
        new DatePickerDialog(context, R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                editText.setText(i+"-"+i1+"-"+i2);
            }
        } , Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                //      pickerDialog.setTitle("Select Date");
                .show();
    }

    public void ClickMaleS(View view) {
        this.Gender="Male";
    }

    public void ClickFemaleS(View view) {
        this.Gender="Female";
    }
}