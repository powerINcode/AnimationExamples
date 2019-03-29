package com.example.powerincode.screens.activityTransition;

import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.powerincode.animatingtest.R;

public class ActivityTransitions2 extends AppCompatActivity {
    // Для задания анимации переходов, необходимо установить ее в стиле темы или же кодом в самой
    // активити. После чего вызвать эту активити с передачей ActivityOptions

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions2);

        mImageView = (ImageView) findViewById(R.id.imageView4);

        if (getIntent() != null) {

            String imageResId = getIntent().getStringExtra("image");
            int resId = getResources().getIdentifier(imageResId, "drawable", "android");
            mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), resId, null));
        }

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.tv_description);
        slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        slide.setDuration(250);
        getWindow().setEnterTransition(slide);

    }
}
