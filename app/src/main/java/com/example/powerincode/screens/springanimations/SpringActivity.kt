package com.example.powerincode.screens.springanimations

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.powerincode.animatingtest.R
import com.example.powerincode.animatingtest.extensions.doOnLayout
import kotlinx.android.synthetic.main.activity_spring.*

class SpringActivity : AppCompatActivity() {
    private var damping: Float = 0.5f
    private var stiffness: Float = 0.5f

    private var dX = 0f
    private var dY = 0f

    private lateinit var chain: SpringChain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring)

        chain = SpringChain(dragButton, button1, 0f, 0f)
        chain.addSubHolder(button2)


        dragButton.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY

                    view.animate().x(newX).y(newY).setDuration(0).start()


                    chain.animateToFinalPosition(newX, newY)
                }
            }

            return@setOnTouchListener true
        }

        updateDamping(convertDampingSeekValue(dampingSeeker.progress))
        dampingSeeker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateDamping(convertDampingSeekValue(dampingSeeker.progress))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        updateStiffness(convertStiffnessSeekValue(stiffnessSeeker.progress))
        stiffnessSeeker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateStiffness(convertStiffnessSeekValue(stiffnessSeeker.progress))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    private fun updateDamping(value: Float) {
        damping = value
        dampingTextView.text = damping.toString()

        chain.damping = value
    }

    private fun updateStiffness(value: Float) {
        stiffness = value
        stiffnessTextView.text = stiffness.toString()

        chain.stiffness = value
    }

    private fun convertDampingSeekValue(value: Int): Float = value / dampingSeeker.max.toFloat()
    private fun convertStiffnessSeekValue(value: Int): Float = value.toFloat()//value / 100f


    private class SpringChain(val baseView: View, val targetView: View, damping: Float, stiffness: Float) {
        private val basePosition = IntArray(2)
        private val targetPosition = IntArray(2)

        val offset: Point
            get() {
                return Point(basePosition.first() - targetPosition.first(), basePosition.last() - targetPosition.last())
            }
        val animation: Pair<SpringAnimation, SpringAnimation>
        var subChain: SpringChain? = null
            set(value) {
                field = value
                value?.damping = damping
                value?.stiffness = stiffness

                animation.first.addUpdateListener { _, position, _ ->
                    value?.animateToFinalPositionX(position)
                }

                animation.second.addUpdateListener { _, position, _ ->
                    value?.animateToFinalPositionY(position)
                }
            }

        var damping: Float = 0.0f
            set(value) {
                field = value

                animation.first.spring.dampingRatio = value
                animation.second.spring.dampingRatio = value

                subChain?.let {
                    subChain?.damping = damping
                }
            }

        var stiffness: Float = 0.1f
            set(value) {
                field = if (value == 0f) 0.1f else value

                animation.first.spring.stiffness = stiffness
                animation.second.spring.stiffness = stiffness

                subChain?.let {
                    subChain?.stiffness = stiffness
                }
            }


        init {
            animation = Pair(
                createAnimation(targetView, DynamicAnimation.X),
                createAnimation(targetView, DynamicAnimation.Y)
            )

            baseView.doOnLayout {
                basePosition[0] = baseView.x.toInt()
                basePosition[1] = baseView.y.toInt()
            }

            targetView.doOnLayout {
                targetPosition[0] = targetView.x.toInt()
                targetPosition[1] = targetView.y.toInt()
            }

            this.damping = damping
            this.stiffness = stiffness
        }

        fun animateToFinalPosition(x: Float, y: Float) {
            animateToFinalPositionX(x)
            animateToFinalPositionY(y)

        }

        fun animateToFinalPositionX(x: Float) {
            animation.first.animateToFinalPosition(x - offset.x)
        }

        fun animateToFinalPositionY(y: Float) {
            animation.second.animateToFinalPosition(y - offset.y)
        }

        fun addSubHolder(view: View) {
            var target: SpringChain = this

            while (target.subChain != null) {
                target = target.subChain!!
            }

            target.subChain = SpringChain(target.targetView, view, damping, stiffness)
        }


        private fun createAnimation(view: View, property: FloatPropertyCompat<View>): SpringAnimation {
            return SpringAnimation(view, property).apply {
                spring = SpringForce().apply {
                    dampingRatio = this@SpringChain.damping
                    stiffness = this@SpringChain.stiffness
                }
            }
        }
    }
}