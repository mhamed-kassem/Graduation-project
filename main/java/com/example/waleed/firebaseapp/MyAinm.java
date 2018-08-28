package com.example.waleed.firebaseapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Sara Alkurdy on 15/06/18.
 */

public class MyAinm {

    public static void fade(final ClickAction action , final View view, final int duration, float... values){
        ObjectAnimator offl = ObjectAnimator.ofFloat(view, "alpha", values[0], values[1]).setDuration(duration);
        offl.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                action.click();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        offl.start();
    }
    public interface ClickAction {
        void click();
    }
}
