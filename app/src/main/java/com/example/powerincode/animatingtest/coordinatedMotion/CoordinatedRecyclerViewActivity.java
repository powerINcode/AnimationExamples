package com.example.powerincode.animatingtest.coordinatedMotion;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.powerincode.animatingtest.R;
import com.example.powerincode.animatingtest.adapters.RecyclerAdapter;


public class CoordinatedRecyclerViewActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinated_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerAdapter(this, 20);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRecyclerView();
            }
        }, 200);


        Slide slide = new Slide(Gravity.RIGHT);

    }

    void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        goRecyclerUpAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        goRecyclerDownAnimation();
    }

    private void goRecyclerUpAnimation() {
        LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int firstViewPosition = lm.findFirstCompletelyVisibleItemPosition();
        int lastViewPosition = lm.findLastVisibleItemPosition();
        int offset = mRecyclerView.getHeight() / 2;

        if (firstViewPosition >= 0 && lastViewPosition > 0) {
            for (int i = firstViewPosition, cnt = 1; i <= lastViewPosition; i++, cnt++) {
                View view = mRecyclerView.getChildAt(i);
                view.setAlpha(0);

                view.setTranslationY(offset * cnt);

                view.animate()
                        .alpha(1)
                        .translationY(0)
                        .setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in))
                        .setDuration(1000)
                        .start();
            }
        }
    }

    private void goRecyclerDownAnimation() {
        LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int firstViewPosition = lm.findFirstCompletelyVisibleItemPosition();
        int lastViewPosition = lm.findLastVisibleItemPosition();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int finalPosition = metrics.heightPixels;

        for (int i = lastViewPosition, cnt = 1; i >= firstViewPosition; i--, cnt++) {
            View view = mRecyclerView.getChildAt(i);

            view.animate()
                    .alpha(0)
                    .setStartDelay(50 * cnt)
                    .translationY(finalPosition)
                    .setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_linear_in))
                    .setDuration(300)
                    .start();
        }
    }
}
