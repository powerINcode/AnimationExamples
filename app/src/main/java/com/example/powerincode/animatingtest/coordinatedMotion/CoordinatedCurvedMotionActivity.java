package com.example.powerincode.animatingtest.coordinatedMotion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.powerincode.animatingtest.R;
import com.example.powerincode.animatingtest.adapters.RecyclerAdapter;

public class CoordinatedCurvedMotionActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerAdapterEvent {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinated_curved_motion);

        mRecyclerView = (RecyclerView) findViewById(R.id.root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerAdapter(this, 20);
        mAdapter.setEventListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int firstItemPosition = lm.findFirstVisibleItemPosition();
                int lastItemPosition = lm.findLastVisibleItemPosition();

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                int offset = metrics.widthPixels;

                for (int i = firstItemPosition, cnt = 0; i <= lastItemPosition; i++, cnt++) {
                    View view = mRecyclerView.getChildAt(i);
                    view.setAlpha(0);
                    view.setTranslationX(offset);

                    view.animate()
                            .alpha(1)
                            .translationX(0)
                            .setStartDelay(85 * cnt)
                            .setInterpolator(AnimationUtils.loadInterpolator(CoordinatedCurvedMotionActivity.this, android.R.interpolator.linear_out_slow_in))
                            .setDuration(900)
                            .start();
                }

            }
        }, 200);

//        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_bottom));

    }

    @Override
    public void onHolderTap(View holder, String color) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, holder, holder.getTransitionName()).toBundle();
        Intent intent = new Intent(this, CoordinatedCurvedMotionActivity2.class);
        intent.putExtra("color", color);
        startActivity(intent, bundle);
    }
}
