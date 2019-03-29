package com.example.powerincode.screens.transformingSurface;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.example.powerincode.animatingtest.R;

public class TransformingSurfaceActivity extends AppCompatActivity {

    boolean mIsSmall = true;
    float mLargeScale = 3.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transforming_surface);

        animate();
    }

    void animate() {
        View square = findViewById(R.id.v_square);

        Interpolator interpolator = mIsSmall
                ? AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in)
                : AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(square, View.SCALE_X, mIsSmall ? 1 : mLargeScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(square, View.SCALE_Y, mIsSmall ? 1 : mLargeScale);

        scaleX.setInterpolator(interpolator);
        scaleY.setInterpolator(interpolator);

        scaleX.setDuration(200);
        scaleY.setDuration(600);

        scaleY.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsSmall = !mIsSmall;
                        animate();
                    }
                }, 500);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        scaleX.start();
        scaleY.start();
    }
}
