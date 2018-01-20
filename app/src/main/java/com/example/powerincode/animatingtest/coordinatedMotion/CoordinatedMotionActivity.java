package com.example.powerincode.animatingtest.coordinatedMotion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.powerincode.animatingtest.R;

public class CoordinatedMotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinated_motion);
    }

    private <T extends AppCompatActivity> void startActivity(Class<T> activity) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(new Intent(this, activity));
    }

    public void onCoordinatedRecyclerViewTap(View view) {
        startActivity(CoordinatedRecyclerViewActivity.class);
    }

    public void onCurvedMotionTap(View view) {
        startActivity(CoordinatedCurvedMotionActivity.class);
    }
}
