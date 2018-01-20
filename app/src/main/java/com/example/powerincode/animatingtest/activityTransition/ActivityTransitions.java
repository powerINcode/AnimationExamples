package com.example.powerincode.animatingtest.activityTransition;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.powerincode.animatingtest.R;

public class ActivityTransitions extends AppCompatActivity {
    // Для задания анимации переходов, необходимо установить ее в стиле темы или же кодом в самой
    // активити. После чего вызвать эту активити с передачей ActivityOptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);
    }

    public void goToActivity2(View view) {
        ImageButton sender = (ImageButton) view;
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle();
        Intent intent = new Intent(this, ActivityTransitions2.class);
        intent.putExtra("image", String.valueOf(sender.getTag()));
        startActivity(intent, bundle);
    }
}
