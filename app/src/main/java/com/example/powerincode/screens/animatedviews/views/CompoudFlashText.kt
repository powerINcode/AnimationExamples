package com.example.powerincode.screens.animatedviews.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CompoudFlashText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val staffPaint: Paint

    init {
        staffPaint = Paint().apply {
            color = Color.parseColor("#FC3554")
            style = Paint.Style.FILL
        }
    }
}