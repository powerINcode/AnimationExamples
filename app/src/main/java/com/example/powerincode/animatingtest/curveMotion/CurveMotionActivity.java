package com.example.powerincode.animatingtest.curveMotion;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import com.example.powerincode.animatingtest.R;


public class CurveMotionActivity extends AppCompatActivity {

    boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve_motion);
    }

    public void onFabClick(final View view) {
        final View player = (View) findViewById(R.id.v_player);
        final View player_active = (View) findViewById(R.id.v_player_active);
        final int duration = 300;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float moveX = 0;
        float transitionY = 0;

        Interpolator slowIn = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        Interpolator fastOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_linear_in);

        moveX = -view.getWidth();
        transitionY = player.getHeight() / 2;


        ObjectAnimator animationX = ObjectAnimator.ofFloat(view, View.X, moveX);
        animationX.setDuration(duration);
        animationX.setInterpolator(fastOut);

        ObjectAnimator animationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, transitionY);
        animationY.setDuration(duration);
        animationY.setInterpolator(slowIn);

        view.animate()
                .alpha(0)
                .setDuration(duration)
                .setInterpolator(fastOut)
                .start();


        animationX.start();
        animationY.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int cx = 0;
                int cy = player_active.getHeight() / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(player_active.getWidth(), player_active.getHeight());

                // create the animator for this view (the start radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(player_active, cx, cy, 0, finalRadius);

                // make the view visible and start the animation
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                player_active.setVisibility(View.VISIBLE);
                anim.setDuration(duration/2);
                anim.start();
            }
        }, duration);

    }

    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
