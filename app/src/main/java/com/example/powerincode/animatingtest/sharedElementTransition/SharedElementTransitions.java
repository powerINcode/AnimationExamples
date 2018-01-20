package com.example.powerincode.animatingtest.sharedElementTransition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.powerincode.animatingtest.R;

public class SharedElementTransitions extends AppCompatActivity {

    // Для задания анимации переходов, необходимо установить ее в стиле темы или же кодом в самой
    // активити. После чего вызвать эту активити с передачей ActivityOptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_transitions);
    }

    public void goToActivity2(View view) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, view, view.getTransitionName()).toBundle();
        Intent intent = new Intent(this, SharedElementTransitions2.class);
        intent.putExtra("image", String.valueOf(view.getTag()));
        startActivity(intent, bundle);
    }
}
