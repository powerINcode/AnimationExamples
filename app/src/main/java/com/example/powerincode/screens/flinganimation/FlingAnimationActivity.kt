package com.example.powerincode.screens.flinganimation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.example.powerincode.animatingtest.R

/**
 * Created by powerincode on 29/03/2019.
 */

class FlingAnimationActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fling_animation)

        val flingView = findViewById<ImageView>(R.id.iv_fling_activity)
        val root = findViewById<ViewGroup>(R.id.cl_fling_activity_root)

        val gestureListener = object: GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                val flingX = FlingAnimation(flingView, DynamicAnimation.X)
                flingX.setStartVelocity(velocityX)
                    .setMinValue(0f) // minimum translationX property
                    .setMaxValue((root.width - flingView.width).toFloat())  // maximum translationX property
                    .setFriction(1.1f)
                    .start()

                val flingY = FlingAnimation(flingView, DynamicAnimation.Y)
                flingY.setStartVelocity(velocityY)
                    .setMinValue(0f) // minimum translationX property
                    .setMaxValue((root.height - flingView.height).toFloat())  // maximum translationX property
                    .setFriction(1.1f)
                    .start()
                return true
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }
        }

        val detector = GestureDetectorCompat(this, gestureListener)
        flingView.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return detector.onTouchEvent(p1)
            }
        })
    }
}