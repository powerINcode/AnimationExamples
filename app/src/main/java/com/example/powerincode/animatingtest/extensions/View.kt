package com.example.powerincode.animatingtest.extensions

import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by powermanincode on 29/03/2019.
 */

fun View.doOnLayout(block : () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block()
        }
    })
}