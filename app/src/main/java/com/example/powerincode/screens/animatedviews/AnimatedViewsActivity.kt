package com.example.powerincode.screens.animatedviews

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.powerincode.animatingtest.R
import kotlinx.android.synthetic.main.activity_animated_controls.*

class AnimatedViewsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animated_controls)

        heartView.setOnClickListener {
            var stateListDrawable = it.background as StateListDrawable
            val animatedImageDrawable = stateListDrawable.current as? AnimatedVectorDrawable
            animatedImageDrawable?.start()
        }

        flashingView.setOnClickListener {
            flashingView.start()
        }

    }
}