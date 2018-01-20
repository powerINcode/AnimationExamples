package com.example.powerincode.animatingtest.coordinatedMotion;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.powerincode.animatingtest.R;

public class CoordinatedCurvedMotionActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinated_curved_motion2);



        if (getIntent() != null) {
            String color = getIntent().getStringExtra("color");

            @SuppressLint("WrongViewCast")
            GradientDrawable avatarBackground = (GradientDrawable) findViewById(R.id.v_avatar).getBackground();
            avatarBackground.mutate();
            avatarBackground.setColor(Color.parseColor(color));
        }

        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.shared_element_curve);
        getWindow().setSharedElementEnterTransition(transition);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewGroup group = findViewById(R.id.surface_container);

                for (int i = 0; i < group.getChildCount(); i++) {
                    View view = group.getChildAt(i);
                    view.setVisibility(View.VISIBLE);
                    view.setAlpha(0);
                    view.setTranslationY(view.getHeight());

                    view.animate()
                            .setStartDelay(100 * i)
                            .alpha(1)
                            .translationY(0)
                            .setDuration(350)
                            .setInterpolator(AnimationUtils.loadInterpolator(CoordinatedCurvedMotionActivity2.this, android.R.interpolator.linear_out_slow_in))
                            .start();
                }
            }
        }, 200);
    }
}
