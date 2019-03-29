package com.example.powerincode.screens.sharedElementTransition;

import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.powerincode.animatingtest.R;

public class SharedElementTransitions2 extends AppCompatActivity {

    // Для задания анимации переходов, необходимо установить ее в стиле темы или же кодом в самой
    // активити. После чего вызвать эту активити с передачей ActivityOptions

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_transitions2);

        mImageView = (ImageView) findViewById(R.id.imageView4);

        if (getIntent() != null) {

            String imageResId = getIntent().getStringExtra("image");
            int resId = getResources().getIdentifier(imageResId, "drawable", "android");
            mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), resId, null));
        }


        Fade fade = new Fade();
        fade.setStartDelay(300);
        fade.addTarget(findViewById(R.id.tv_description));
        fade.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        fade.setDuration(500);

        getWindow().setEnterTransition(fade);
    }
}
