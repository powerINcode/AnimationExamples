package com.example.powerincode.screens.crossfireFade;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.powerincode.animatingtest.R;

public class CrossFireAnimation extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextView;

    boolean mIsLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_fire_animation);

        mProgressBar = (ProgressBar) findViewById(R.id.crossfire_pb);
        mTextView = (TextView) findViewById(R.id.crossfire_tv);
        mTextView.setAlpha(0);
    }

    public void onSwap(View view) {
        View showView = mIsLoading ? mTextView : mProgressBar;
        View hideView = mIsLoading ? mProgressBar : mTextView;

        showView.animate()
                .alpha(1)
                .setDuration(200);

        hideView.animate()
                .alpha(0)
                .setDuration(200);

        mIsLoading = !mIsLoading;
    }
}
