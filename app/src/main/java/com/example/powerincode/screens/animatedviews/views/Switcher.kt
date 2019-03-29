package com.example.powerincode.screens.animatedviews.views

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.annotation.ColorInt
import com.example.powerincode.animatingtest.R


/**
 * Created by powermanincode
 * Design of control provided by Oleg Frolov (https://dribbble.com/shots/5429846-Switcher-XLIV)
 */
class SwitcherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Main state of control
    var isChecked: Boolean = false

    // Initial click area color
    @ColorInt
    private var clickColor = 0

    // Initial switcher background color in ON state
    @ColorInt
    private var bgOnColor = 0

    // Initial switcher background color in OFF state
    @ColorInt
    private var bgOffColor = 0

    // Current switcher background color
    @ColorInt
    private var currentSwitcherColor: Int = bgOffColor
        private set(value) {
            field = value
            switcherPaint.color = value
            clipPaint.color = value
        }

    private var currentClickColor: Int = 0
        private set(value) {
            field = value
            clickPaint.color = value
        }

    /**
     * Indicator background color
     */
    @ColorInt
    private var indicatorColor = 0


    private val switcherRect = RectF()
    private var switcherCornerRadius: Float = 0f
    private val switcherPaint = Paint()

    private val indicatorRect = RectF()
    private val indicatorPaint = Paint()
    private var indicatorWidth: Float = 0f
    private var indicatorCornerRadius: Float = 0f

    private val clipPaint = Paint()
    private val indicatorClipRect = RectF()
    private val indicatorClipHeight: Float
        get() { return height * 0.23f }

    private val clickPaint = Paint()
    private val clickRect = RectF()

    private var animatorSet: AnimatorSet? = null

    private var canvasTranslateX = 0f

    private val desiredWidth = 80.px()
    private val desiredHeight = 40.px()

    init {
        isClickable = true
        isFocusable = true

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SwitcherView, defStyleAttr, R.style.Switcher)

        try {
            isChecked = typedArray.getBoolean(R.styleable.SwitcherView_android_checked, false)
            bgOnColor = typedArray.getColor(R.styleable.SwitcherView_on_color, 0)
            bgOffColor = typedArray.getColor(R.styleable.SwitcherView_off_color, 0)
            indicatorColor = typedArray.getColor(R.styleable.SwitcherView_indicator_color, 0)
            clickColor = typedArray.getColor(R.styleable.SwitcherView_click_color, 0)
        } finally {
            typedArray.recycle()
        }


        currentSwitcherColor = bgOffColor
        switcherPaint.isAntiAlias = true

        indicatorPaint.isAntiAlias = true
        indicatorPaint.color = indicatorColor
        indicatorPaint.style = Paint.Style.FILL_AND_STROKE

        clipPaint.isAntiAlias = true
        clipPaint.style = Paint.Style.FILL

        clickPaint.isAntiAlias = true
        clickPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return
        canvas.drawRoundRect(switcherRect, switcherCornerRadius, switcherCornerRadius, switcherPaint)

        canvas.save()
        canvas.translate(canvasTranslateX, 0f)
        canvas.drawRoundRect(indicatorRect, indicatorCornerRadius, indicatorCornerRadius, indicatorPaint)

        if (indicatorClipRect.width() > indicatorWidth / 3) {
            canvas.drawRoundRect(indicatorClipRect, indicatorClipHeight, indicatorClipHeight, clipPaint)
        }

        canvas.restore()

        canvas.drawRoundRect(clickRect, height / 2f, height / 2f, clickPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desireWidth = desiredWidth
        val desireHeight = desiredHeight

        val width = processMeasure(desireWidth, widthMeasureSpec)
        val height = processMeasure(desireHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val topBottom = (h * 0.24f).toInt()
        val startBottom = (w * 0.24f).toInt()
        setPadding(startBottom, topBottom, startBottom, topBottom)

        with(switcherRect) {
            right = w.toFloat()
            bottom = h.toFloat()
        }

        switcherCornerRadius = h / 2f

        indicatorWidth = w * 0.05f
        indicatorPaint.strokeWidth = h * 0.23f

        indicatorRect.top = getIndicatorStateRect(isChecked).top
        indicatorRect.bottom = getIndicatorStateRect(isChecked).bottom

        forceCheck(isChecked)

        indicatorCornerRadius = (indicatorRect.bottom - indicatorRect.top) / 2f
    }

    override fun performClick(): Boolean {
        var range = listOf(0f, 1f)
        isChecked = !isChecked

        if (!isChecked) {
            range = range.reversed()
        }

        with(getClickRect(isChecked)) {
            clickRect.left = left
            clickRect.top = top
            clickRect.right = right
            clickRect.bottom = bottom
        }


        val colorAnimator = ValueAnimator.ofFloat(range.first(), range.last()).apply {
            duration = 300
            addUpdateListener {
                val value = it.animatedValue as Float
                setColorBy(value)
            }
        }

        val colorClickAnimator = ValueAnimator.ofInt().apply {
            duration = 300
            setIntValues(Color.alpha(clickColor), 0)
            addUpdateListener {
                val value = it.animatedValue as Int
                currentClickColor = Color.argb(value, Color.red(clickColor), Color.green(clickColor), Color.blue(clickColor))
            }
        }

        val translateAnimator = ValueAnimator.ofFloat(range.first(), range.last()).apply {
            duration = 200
            addUpdateListener {
                val value = it.animatedValue as Float
                setTranslateBy(value)
                postInvalidateOnAnimation()
            }
        }

        val indicatorAnimator = ValueAnimator.ofFloat(range.first(), range.last()).apply {
            duration = 800
            interpolator = BounceInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float

                setIndicatorStateBy(value)
                postInvalidateOnAnimation()
            }
        }

        if (animatorSet == null) {
            animatorSet = AnimatorSet()
        }

        animatorSet?.cancel()
        animatorSet?.playTogether(colorClickAnimator, colorAnimator, indicatorAnimator, translateAnimator)
        animatorSet?.start()


        return super.performClick()
    }

    private fun forceCheck(isChecked: Boolean) {
        val progress = if (isChecked) 1f else 0f
        setColorBy(progress)
        setTranslateBy(progress)
        setIndicatorStateBy(progress)
    }

    private fun getIndicatorStateRect(isChecked: Boolean): RectF {
        val result = RectF()
        result.top = paddingTop.toFloat()
        result.bottom = height - paddingBottom.toFloat()

        if (isChecked) {
            result.left = width - switcherCornerRadius - result.height() / 2
            result.right = width.toFloat() - switcherCornerRadius + result.height() / 2
        } else {
            result.left = width - switcherCornerRadius - indicatorWidth / 2
            result.right = width.toFloat() - switcherCornerRadius + indicatorWidth / 2
        }

        return result
    }

    private fun getClickRect(isChecked: Boolean): RectF {
        val result = RectF()
        result.top = paddingTop.toFloat()
        result.bottom = height - paddingBottom.toFloat()

        if (isChecked) {
            result.left = switcherCornerRadius / 2
            result.right = switcherCornerRadius / 2 + result.height()
        } else {
            result.left = width - switcherCornerRadius / 2 - result.height()
            result.right = width.toFloat() - switcherCornerRadius / 2
        }

        return result
    }

    private fun setColorBy(progress: Float) {
        currentSwitcherColor = ArgbEvaluator().evaluate(progress, bgOffColor, bgOnColor) as Int
    }

    private fun setTranslateBy(progress: Float) {
        canvasTranslateX = lerp(0f, -(width - switcherCornerRadius * 2), progress)
    }

    private fun setIndicatorStateBy(progress: Float) {
        indicatorRect.left = lerp(getIndicatorStateRect(false).left, getIndicatorStateRect(true).left, progress)
        indicatorRect.right = lerp(getIndicatorStateRect(false).right, getIndicatorStateRect(true).right, progress)

        val radius = indicatorClipHeight / 2
        indicatorClipRect.top = lerp(indicatorRect.centerY(), indicatorRect.top + radius, progress)
        indicatorClipRect.left = lerp(indicatorRect.centerX(), indicatorRect.left + radius, progress)
        indicatorClipRect.right = lerp(indicatorRect.centerX(), indicatorRect.right - radius, progress)
        indicatorClipRect.bottom = lerp(indicatorRect.centerY(), indicatorRect.bottom - radius, progress)
    }

    private fun processMeasure(desiredSize: Int, spec: Int): Int {
        var result: Int
        val modeSize = MeasureSpec.getMode(spec)
        val sizeValue = MeasureSpec.getSize(spec)

        if (modeSize == MeasureSpec.EXACTLY) {
            result = sizeValue
        } else {
            result = desiredSize
            if (modeSize == MeasureSpec.AT_MOST) {
                result = Math.min(result, sizeValue)
            }
        }

        if (desiredSize < result) {
            Log.e("SwitcherView:", "View is too small, some content might be not visible or cut")
        }

        return result
    }

    private fun lerp(min: Float, max: Float, k: Float): Float {
        return ((max - min) * k) + min
    }

    private fun Int.px(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

}