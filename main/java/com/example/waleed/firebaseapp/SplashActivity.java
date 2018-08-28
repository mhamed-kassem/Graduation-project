package com.example.waleed.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

/**
 * Created by Sara Alkurdy on 01-Mar-18.
 */

public class SplashActivity extends AppCompatActivity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    private ProgressBar progress;
    private ProgressBar progress_bar;

    private Animation an;
    private Animation an2;
    private ProgressBar horizontal_progress;
    private Thread thread_progress;
    private ImageView image_splash_1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        VIEWS();
    }

    private void VIEWS() {

        image_splash_1 = (ImageView)findViewById(R.id.image_rotate_1);

        an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        image_splash_1.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image_splash_1.startAnimation(an2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        horizontal_progress = (ProgressBar)findViewById(R.id.horizontal_progress);
        horizontal_progress.setMax(100);

        thread_progress = new Thread(new Runnable() {
            public int progress_value;
            @Override
            public void run() {
                progress_value = 0;
                while (progress_value<100){
                    try {
                        sleep(450);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    horizontal_progress.setProgress(progress_value);
                    progress_value += 10;
                }
                Intent intent = new Intent(getBaseContext(),HomeActivityList.class);
                startActivity(intent);
                finish();
            }
        });
        thread_progress.start();
    }
}
