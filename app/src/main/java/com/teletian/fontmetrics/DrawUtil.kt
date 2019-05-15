package com.teletian.fontmetrics

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

object DrawUtil {

    fun drawCenterText(text: String, rect: RectF, canvas: Canvas, paint: Paint) {
        val centerX = (rect.left + rect.right) / 2
        val centerY = (rect.top + rect.bottom) / 2
        val startX = centerX - getTextWidth(text, paint) / 2
        val baseline = centerY - (paint.ascent() + paint.descent()) / 2
        canvas.drawText(text, startX, baseline, paint)
    }

    fun getTextWidth(text: String, paint: Paint): Float {
        return if (text.isEmpty()) 0.0f else paint.measureText(text)
    }
}