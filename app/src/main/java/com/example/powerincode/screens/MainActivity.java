package com.example.powerincode.screens;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.powerincode.animatingtest.R;
import com.example.powerincode.screens.animatedviews.AnimatedViewsActivity;
import com.example.powerincode.screens.flinganimation.FlingAnimationActivity;
import com.example.powerincode.screens.springanimations.SpringActivity;
import com.example.powerincode.screens.vectorAnimation.VectorAnimatingActivity;
import com.example.powerincode.screens.activityTransition.ActivityTransitions;
import com.example.powerincode.screens.coordinatedMotion.CoordinatedMotionActivity;
import com.example.powerincode.screens.coordinatorLayout.CoordinatorLayoutActivity;
import com.example.powerincode.screens.crossfireFade.CrossFireAnimation;
import com.example.powerincode.screens.curveMotion.CurveMotionActivity;
import com.example.powerincode.screens.instructiveMotion.InstructiveMotion;
import com.example.powerincode.screens.interpolating.InterpolatingActivity;
import com.example.powerincode.screens.sceneChange.SceneChangeActivity;
import com.example.powerincode.screens.sharedElementTransition.SharedElementTransitions;
import com.example.powerincode.screens.transformingSurface.TransformingSurfaceActivity;
import com.example.powerincode.screens.transitionManager.TransitionManagerActivity;

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

    public void onFlingAnimationTap(View view) {
        startActivity(FlingAnimationActivity.class);
    }

    public void onSpringAnimationTap(View view) {
        startActivity(SpringActivity.class);
    }

    public void onAnimationViewsTap(View view) {
        startActivity(AnimatedViewsActivity.class);
    }

    public void onCurveMotionTap(View view) {
        startActivity(CurveMotionActivity.class);
    }

    public void onVectorAnimatingTap(View view) {
        startActivity(VectorAnimatingActivity.class);
    }

    public void onCoordinatorLayoutTap(View view) {
        startActivity(CoordinatorLayoutActivity.class);
    }
}
