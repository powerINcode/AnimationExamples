package com.example.powerincode.screens.animatedviews.views

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.powerincode.animatingtest.R

class ClippingTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var text: String = ""
    set(value) {
        field = value
        requestLayout()
    }
    var slideDuration: Long = 1400

    private val boundsRect = Rect()
    private val textPaint: TextPaint
    private var textSize: Float = 0f
    private var textColor: Int = Color.BLACK
    private var textStyle: Int = Typeface.NORMAL
    private var direction: Direction = Direction.LEFT

    private var offsetY: Float = 0f
    private var offsetX: Float = 0f

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ClippingTextView, 0, 0)
        try {
            textSize = typedArray.getDimension(R.styleable.ClippingTextView_android_textSize, 14 * resources.displayMetrics.density)
            text = typedArray.getString(R.styleable.ClippingTextView_android_text) ?: ""
            textColor = typedArray.getColor(R.styleable.ClippingTextView_android_textColor, Color.BLACK)
            slideDuration = typedArray.getInt(R.styleable.ClippingTextView_slideDuration, slideDuration.toInt()).toLong()

            if(typedArray.hasValue(R.styleable.ClippingTextView_android_textStyle)) {
                textStyle = typedArray.getInt(R.styleable.ClippingTextView_android_textStyle, textStyle);
            }

            if(typedArray.hasValue(R.styleable.ClippingTextView_slide_direction)) {
                direction = Direction.get(
                    typedArray.getInt(
                        R.styleable.ClippingTextView_slide_direction,
                        0
                    )
                )
            }
        } catch (e: Exception) {
            typedArray.recycle()
        }



        textPaint = TextPaint().apply {
            color = textColor
            textSize = this@ClippingTextView.textSize
            typeface = Typeface.create(Typeface.DEFAULT, textStyle)
            style = Paint.Style.FILL
            textAlign = Paint.Align.LEFT
        }

        textPaint.measureText(text)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawText(text, offsetX, height.toFloat() + offsetY - getTextOffset(), textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var width = size

        textPaint.getTextBounds(text, 0, text.length, boundsRect)
        boundsRect.right += getTextOffset() * resources.displayMetrics.density.toInt()
        boundsRect.bottom += getTextOffset() * resources.displayMetrics.density.toInt()

        if (mode != MeasureSpec.EXACTLY) {
            width = Math.min(boundsRect.width(), size)
        }

        setMeasuredDimension(width, boundsRect.height())
    }

    fun start() {
        if (isHorizontal()) {
            ValueAnimator.ofFloat(width.toFloat(), 0f).apply {
                duration = slideDuration
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    val value = it.animatedValue as Float
                    offsetX = if (direction == Direction.RIGHT) value else -value
                    postInvalidateOnAnimation()
                }
            }
                .start()
        } else {
            ValueAnimator.ofFloat(textSize, 0f).apply {
                duration = slideDuration
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    val value = it.animatedValue as Float
                    offsetY = if (direction == Direction.TOP) -value else value
                    postInvalidateOnAnimation()
                }
            }
                .start()
        }
    }

    fun reverse() {
        if (isHorizontal()) {
            ValueAnimator.ofFloat(width.toFloat(), 0f).apply {
                duration = slideDuration
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    val value = it.animatedValue as Float
                    offsetX = if (direction == Direction.RIGHT) width - value else value - width
                    postInvalidateOnAnimation()
                }
            }
                .start()
        } else {
            ValueAnimator.ofFloat(textSize, 0f).apply {
                duration = slideDuration
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    val value = it.animatedValue as Float
                    offsetY = if (direction == Direction.TOP) value - textSize else textSize - value
                    postInvalidateOnAnimation()
                }
            }
                .start()
        }
    }

    private fun isHorizontal() = direction == Direction.LEFT || direction == Direction.RIGHT
    private fun getTextOffset(): Int = 4 * resources.displayMetrics.density.toInt()

    enum class Direction {
        LEFT, TOP, RIGHT, BOTTOM;

        companion object {
            fun get(value: Int) =  when(value) {
                1 -> TOP
                2 -> RIGHT
                3 -> BOTTOM
                else -> LEFT
            }
        }
    }

}