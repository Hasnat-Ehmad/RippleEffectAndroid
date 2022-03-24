package com.hasnat.voicerippleexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(
    context: Context?,
    attrs: AttributeSet?,
    colour: Int,
    rippleType: Int,
    private val rippleStrokeWidth: Float
) : View(context, attrs) {

    private val paint = Paint()

    init {
        if (context == null) throw IllegalArgumentException("Context is null.")
        if (attrs == null) throw IllegalArgumentException("Attribute set is null.")

        visibility = View.INVISIBLE

        paint.apply {
            isAntiAlias = true
            color = colour
            style = when (rippleType) {
                RippleView.Companion.FillStyle.FILL.type -> {
                    strokeWidth = 0f
                    Paint.Style.FILL
                }
                RippleView.Companion.FillStyle.STROKE.type -> {
                    strokeWidth = rippleStrokeWidth
                    Paint.Style.STROKE
                }
                RippleView.Companion.FillStyle.FILL_AND_STROKE.type -> {
                    strokeWidth = rippleStrokeWidth
                    Paint.Style.FILL_AND_STROKE
                }
                else -> throw IllegalArgumentException("Unknown fill style: $rippleType.")
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val radius: Float = Math.min(width, height) / 2.toFloat()
        canvas?.drawCircle(radius, radius, radius - rippleStrokeWidth, paint)
    }
}