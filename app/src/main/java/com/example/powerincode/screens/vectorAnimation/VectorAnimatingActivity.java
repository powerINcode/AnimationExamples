package com.example.powerincode.screens.vectorAnimation;

import android.graphics.drawable.AnimatedVectorDrawable;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.powerincode.animatingtest.R;

public class VectorAnimatingActivity extends AppCompatActivity {

    boolean isCross = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_animating);


    }

    public void animate(View view) {
        AnimatedVectorDrawable crossToTick = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_cross_to_tick, null);
        AnimatedVectorDrawable tickToCross = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_tick_to_cross, null);

        AnimatedVectorDrawable animatedDrawable = isCross ? crossToTick : tickToCross;
        ImageView image = findViewById(R.id.iv_vector);
        image.setImageDrawable(animatedDrawable);
        animatedDrawable.start();

        isCross = !isCross;
    }
}
