package com.example.powerincode.animatingtest.instructiveMotion;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.example.powerincode.animatingtest.R;

public class InstructiveMotion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instractive_motion);

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();

        int startScrollPosition = 800;

        ObjectAnimator animation = ObjectAnimator.ofInt(findViewById(R.id.sv_content), "scrollY", startScrollPosition)
                .setDuration(400);
        animation.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));

        animation.start();
    }
}
