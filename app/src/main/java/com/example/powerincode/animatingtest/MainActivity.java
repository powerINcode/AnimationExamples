package com.example.powerincode.animatingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.powerincode.animatingtest.VectorAnimation.VectorAnimatingActivity;
import com.example.powerincode.animatingtest.activityTransition.ActivityTransitions;
import com.example.powerincode.animatingtest.coordinatedMotion.CoordinatedMotionActivity;
import com.example.powerincode.animatingtest.crossfireFade.CrossFireAnimation;
import com.example.powerincode.animatingtest.curveMotion.CurveMotionActivity;
import com.example.powerincode.animatingtest.instructiveMotion.InstructiveMotion;
import com.example.powerincode.animatingtest.interpolating.InterpolatingActivity;
import com.example.powerincode.animatingtest.sceneChange.SceneChangeActivity;
import com.example.powerincode.animatingtest.sharedElementTransition.SharedElementTransitions;
import com.example.powerincode.animatingtest.transformingSurface.TransformingSurfaceActivity;
import com.example.powerincode.animatingtest.transitionManager.TransitionManagerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private <T> void startActivity(Class<T> activity) {
        startActivity(new Intent(this, activity));
    }

    public void onCrossfireTap(View view) {
        startActivity(CrossFireAnimation.class);
    }

    public void onTransitionManagerTap(View view) {
        startActivity(TransitionManagerActivity.class);
    }

    public void onSceneChangeTap(View view) {
        startActivity(SceneChangeActivity.class);
    }

    public void onActivityTransitionsTap(View view) {
        startActivity(ActivityTransitions.class);
    }

    public void onSharedElementTransitions(View view) {
        startActivity(SharedElementTransitions.class);
    }

    public void onInstructiveMotionTap(View view) {
        startActivity(InstructiveMotion.class);
    }

    public void onInterpolatingTap(View view) {
        startActivity(InterpolatingActivity.class);
    }

    public void onCoordinatedMotionTap(View view) {
        startActivity(CoordinatedMotionActivity.class);
    }

    public void onTransformingSurfaceTap(View view) {
        startActivity(TransformingSurfaceActivity.class);
    }

    public void onCurveMotionTap(View view) {
        startActivity(CurveMotionActivity.class);
    }

    public void onVectorAnimatingTap(View view) {
        startActivity(VectorAnimatingActivity.class);
    }
}
