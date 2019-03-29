package com.example.powerincode.screens.interpolating;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.powerincode.animatingtest.R;

public class InterpolatingActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolating);

        mTextView = (TextView) findViewById(R.id.tv_interpolating);
        mButton = (Button) findViewById(R.id.btn_interpolating);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                mTextView.setTranslationY(metrics.heightPixels);

                mTextView.animate()
                        .setInterpolator(AnimationUtils.loadInterpolator(InterpolatingActivity.this, android.R.interpolator.linear_out_slow_in))
                        .translationY(0)
                        .setDuration(400);
            }
        });
    }
}
