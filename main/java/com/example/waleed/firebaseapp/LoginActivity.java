package com.example.waleed.firebaseapp;

/**
 * Created by Sara Alkurdy on 01-Mar-18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Context context;
    private NetworkAvailable networkAvailable;
    private boolean isNetworkConnected;

    @BindView(R.id.input_email) EditText email_user;
    @BindView(R.id.input_password) EditText password_user;
    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.link_signup) TextView signupLink;
    @BindView(R.id.profile_photo) CircleImageView profile_photo;
    private SaveSharedPreference save_shared_preference;
    private PreferenceImage preferenceImage;
    private String password;
    private String email;
    private Firebase fireRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        VIEWS();

    }

    private void VIEWS() {

        context = LoginActivity.this;
        preferenceImage = new PreferenceImage(context);
        preferenceImage.Show_Saved_Image(context,profile_photo);

        fireRef =new Firebase("https://fir-app-b2fa9.firebaseio.com/User");

        save_shared_preference = new SaveSharedPreference();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = email_user.getText().toString();
                password = password_user.getText().toString();

                email = EncodeString(email);
                singin(email,password);
            }
        });
        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        networkAvailable = new NetworkAvailable(LoginActivity.this);
        isNetworkConnected = networkAvailable.isNetworkAvailable();
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    public void singin(final String mail, final  String password) {
        Log.d(TAG, "Login");///
        if (!validate()) {
            onLoginFailed();
            return;
        }

        if (!isNetworkConnected) {
            Toast.makeText(context,"Please Connect Your Device With Internet",Toast.LENGTH_LONG).show();
        }

        fireRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                int id=1;
                {
                    if(dataSnapshot.child(mail).exists()) {
                        if (!mail.isEmpty()) {
                            User chilvalue = dataSnapshot.child(mail).getValue(User.class);
                            if (chilvalue.getPassword().equals(password)) {
                                /*** To keep it Login Until press Logout**/
                                save_shared_preference.setEmail(context,email);
                                Intent intent = new Intent(LoginActivity.this, HomeActivityList.class);
                                startActivity(intent);
                                finish();
                            }else {
                                password_user.setError("Wrong Password");
                            }
                        }
                    }
                    else{
                        email_user.setError("This email is not found");
                        //Toast.makeText(LoginActivity.this,"must Register user",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }



    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = email_user.getText().toString();
        String password = password_user.getText().toString();

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

    public void clickToReast(View view) {
//--------------------------------------------------------------------
        final dialog.DialogRestPass dialogRestPass= new dialog.DialogRestPass().getDialogRestPass();

        dialogRestPass.setclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      Toast.makeText(getApplicationContext(),dialogRestPass.option.getText(),Toast.LENGTH_LONG).show();
                final String  mail = EncodeString(dialogRestPass.option.getText().toString());
                if (!isNetworkConnected) {
                    Toast.makeText(context,"Please Connect Your Device With Internet",Toast.LENGTH_LONG).show();
                }

                fireRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(final com.firebase.client.DataSnapshot dataSnapshot) {

                        {
                            if(dataSnapshot.child(mail).exists()) {
                                if (!mail.isEmpty()) {
                                    dialogRestPass.option.setHint("Please Key");
                                   dialogRestPass.option.setText("");
                                   dialogRestPass.buttonO.setText("Step2");
                                   dialogRestPass.setclickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           User chilvalue = dataSnapshot.child(mail).getValue(User.class);
                                           if (chilvalue.getKey().equals(dialogRestPass.option.getText().toString())) {
                                               /*** To keep it Login Until press Logout**/

                                               dialogRestPass.option.setEnabled(false);
                                               dialogRestPass.option.setHint("Password Is");
                                               dialogRestPass.option.setText(chilvalue.getPassword());
                                               dialogRestPass.buttonO.setText("Finsh");
                                               dialogRestPass.setclickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                     dialogRestPass.getDialog().cancel();
                                                   }
                                               });

                                           }else {
                                               dialogRestPass.option.setError("Wrong Key");
                                           }
                                       }
                                   });
                                }
                            }
                            else{
                                dialogRestPass.option.setError("This email is not found");
                                //Toast.makeText(LoginActivity.this,"must Register user",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        dialogRestPass.show(getFragmentManager(),"");
    }
}



