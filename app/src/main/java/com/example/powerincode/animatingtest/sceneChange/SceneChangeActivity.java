package com.example.powerincode.animatingtest.sceneChange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.powerincode.animatingtest.R;

public class SceneChangeActivity extends AppCompatActivity {

    boolean mIsShortInfo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_change);
    }

    public void onInfoClick(View view) {
        int targetScene = mIsShortInfo ? R.layout.scene_picture_full_info : R.layout.scene_picture_short_info;

        TransitionManager.go(Scene.getSceneForLayout((ViewGroup) findViewById(R.id.root),
                targetScene,
                this));

        TransitionInflater.from(this)
                .inflateTransition(R.transition.short_to_full_info);

        mIsShortInfo = !mIsShortInfo;
    }
}
