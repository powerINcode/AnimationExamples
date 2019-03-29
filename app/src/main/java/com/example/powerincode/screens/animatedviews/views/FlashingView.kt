package com.example.powerincode.screens.animatedviews.views

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.powerincode.animatingtest.R
import kotlinx.android.synthetic.main.view_flashing_steps.view.*

class FlashingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val animatedDrawablesMaxDuration = context.resources.getInteger(R.integer.flash_view_slow_line_trim_start_duration).toLong()

    var showTime: Long = 0
    var slideDuration: Long = 1400
    set(value) {
        field = value

        titleView.slideDuration = value
        subTitleView.slideDuration = value
        numberView.slideDuration = value
    }

    var titleText: String = ""
        set(value) {
            field = value
            titleView.text = value
        }

    var subTitleText: String = ""
        set(value) {
            field = value
            subTitleView.text = value
        }

    var numberText: String = ""
        set(value) {
            field = value
            numberView.text = value
        }

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.view_flashing_steps, this, true)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.FlashingView, 0, 0)
        try {
            showTime = typedArray.getInteger(R.styleable.FlashingView_showTimeDuration, showTime.toInt()).toLong()
            slideDuration = typedArray.getInteger(R.styleable.FlashingView_slideDuration, slideDuration.toInt()).toLong()
            titleText = typedArray.getString(R.styleable.FlashingView_titleText) ?: ""
            subTitleText = typedArray.getString(R.styleable.FlashingView_subTitleText) ?: ""
            numberText = typedArray.getString(R.styleable.FlashingView_numberText) ?: ""
        } catch (e: Exception) {
            typedArray.recycle()
        }
    }

    fun start() {
        lineSlowView.rotation = 0f
        lineFastView.rotation = 0f

        numberView.start()
        titleView.start()
        subTitleView.start()

        crossView.alpha = 0f
        crossView.translationX = 0f
        crossView.rotation = 0f
        crossView.scaleX = 1f
        crossView.scaleY = 1f
        crossView.animate()
            .alpha(1f)
            .rotation(360f)
            .translationX((width - crossView.width) - crossView.x)
            .setDuration(getCrossTranslateDuration())
            .setInterpolator(FastOutSlowInInterpolator())
            .withEndAction {
                crossView.animate()
                    .rotation(crossView.rotation + 180)
                    .setDuration(getCrossFadeDuration())
                    .setStartDelay(getCrossDelayDuration())
                    .scaleX(0f)
                    .scaleY(0f)
            }
            .start()



        (lineSlowView.background as AnimatedVectorDrawable).start()
        (lineFastView.background as AnimatedVectorDrawable).start()

        Handler().postDelayed({
            lineSlowView.rotation = 180f
            lineFastView.rotation = 180f


            (lineSlowView.background as AnimatedVectorDrawable).start()
            (lineFastView.background as AnimatedVectorDrawable).start()

            numberView.reverse()
            titleView.reverse()
            subTitleView.reverse()

            crossView.animate()
                .rotation(crossView.rotation - 180)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(getCrossFadeDuration())
                .withEndAction {
                    crossView.animate()
                        .alpha(0f)
                        .rotation(0f)
                        .setDuration(getCrossTranslateDuration())
                        .translationX(0f)
                        .interpolator = FastOutSlowInInterpolator()
                }.start()
        }, animatedDrawablesMaxDuration + showTime)

    }

    private fun getCrossTranslateDuration(): Long = (animatedDrawablesMaxDuration * 0.777).toLong()
    private fun getCrossDelayDuration(): Long = (animatedDrawablesMaxDuration * 0.11).toLong()
    private fun getCrossFadeDuration(): Long = (animatedDrawablesMaxDuration * 0.22).toLong()
}