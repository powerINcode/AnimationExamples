package com.example.powerincode.animatingtest.transitionManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.powerincode.animatingtest.R;

public class TransitionManagerActivity extends AppCompatActivity {

    boolean mIsSlidedUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_manager);
    }

    public void onMicTap(View view) {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.BOTTOM);
        ViewGroup scene = findViewById(R.id.root);
        View micButton = findViewById(R.id.imageButton);

        TransitionManager.beginDelayedTransition(scene, slide);
        micButton.setVisibility(View.INVISIBLE);
    }
}
