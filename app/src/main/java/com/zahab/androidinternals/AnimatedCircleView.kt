package com.zahab.androidinternals

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class AnimatedCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var width = 0
    private var height = 0


    private val startColor = Color.RED
    private val endColor = Color.GREEN


    private val ovalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        setShadowLayer(
            100f,
            0f,
            0f,
            Color.argb(0.4f, 0f,0f,0f)
        )
    }

     private val colorAnimator = ValueAnimator().apply {
        setFloatValues(0f, 1f)
        duration = 1000
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE

    }

    companion object {
        private const val TAG = "AnimatedCircleView"
    }

    private fun interpolateColor(fraction: Float): Int {
        return ArgbEvaluator().evaluate(fraction, startColor, endColor) as Int
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "OnAttachedToWindow: View is Attached to Window")
        colorAnimator.start()
        colorAnimator.addUpdateListener {
            ovalPaint.color = interpolateColor(colorAnimator.animatedValue as Float)
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w
        height = h
        Log.d(TAG, "onSizeChanged: Size($w,$h)")

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure: Constraints($widthMeasureSpec,$heightMeasureSpec)")

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "onLayout: Position($left,$right,$top,$bottom)")

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(
            width /2.0f,
            height/2.0f,
            200f,
            ovalPaint
        )

        Log.d(TAG, "onDrawCalled")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow: View is De-Attached from Window")
        colorAnimator.pause()
        colorAnimator.removeAllUpdateListeners()

    }
}