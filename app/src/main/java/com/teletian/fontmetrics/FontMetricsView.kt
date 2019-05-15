package com.teletian.fontmetrics

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class FontMetricsView : View {

    companion object {
        private const val LINE_STROKE_WIDTH = 5.0f
        private const val LINE_MARGIN = 100.0f
    }

    // 整个 FontMetricsView 的 Rect
    private val viewRect: RectF by lazy { RectF(0.0f, 0.0f, width.toFloat(), height.toFloat()) }

    // 文字 Paint
    private val textPaint: TextPaint by lazy { TextPaint(Paint.ANTI_ALIAS_FLAG) }
    // 文字四周边框 Paint
    private val textBoundsPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    // 文字四周边框
    private val textBounds: Rect by lazy { Rect() }

    // 线的左端用来显示 top、ascent、baseline 等文字的 Paint
    // 线的右端用来显示相对于 baseline 的 distance 的 Paint
    private val lineTextPaint: TextPaint by lazy { TextPaint(Paint.ANTI_ALIAS_FLAG) }
    // 线的 Paint
    private val linePaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    // 线的左端
    private val lineStartX = LINE_MARGIN
    // 线的右端
    private val lineStopX: Float by lazy { width.toFloat() - LINE_MARGIN }

    var showTopLine: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    var showAscentLine: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    var showBaseline: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    var showDescentLine: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    var showBottomLine: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    // text bounds (use measureText method)
    var showTextBoundsLineByMeasureText: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    // text bounds (use getTextBounds method)
    var showTextBoundsLineByGetTextBounds: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initPaint()
    }

    private fun initPaint() {

        textPaint.color = Color.BLACK
        textPaint.textSize = UnitUtil.spToPx(80.0f)

        textBoundsPaint.style = Paint.Style.STROKE
        textBoundsPaint.color = Color.BLUE
        textBoundsPaint.strokeWidth = LINE_STROKE_WIDTH

        lineTextPaint.color = Color.GRAY
        lineTextPaint.textSize = UnitUtil.spToPx(10.0f)

        linePaint.strokeWidth = LINE_STROKE_WIDTH
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw text
        val text = "abcdefg"
        DrawUtil.drawCenterText(text, viewRect, canvas, textPaint)

        // Draw text bounds
        val centerX = width / 2
        val centerY = (viewRect.top + viewRect.bottom) / 2
        val baselineY = centerY - (textPaint.ascent() + textPaint.descent()) / 2
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        if (showTextBoundsLineByGetTextBounds) {
            canvas.drawRect(
                (centerX - textBounds.width() / 2).toFloat(),
                baselineY + textBounds.top, // textBounds.top 是相对于 baseline 的，是负值
                (centerX + textBounds.width() / 2).toFloat(),
                baselineY + textBounds.bottom, // textBounds.bottom 是相对于 baseline 的，是负值
                textBoundsPaint
            )
        }
        if (showTextBoundsLineByMeasureText) {
            canvas.drawRect(
                (centerX - DrawUtil.getTextWidth(text, textPaint) / 2),
                baselineY + textBounds.top,
                (centerX + DrawUtil.getTextWidth(text, textPaint) / 2),
                baselineY + textBounds.bottom,
                textBoundsPaint
            )
        }

        // Draw top
        val distanceStartX = lineStopX + 10
        if (showTopLine) {
            linePaint.color = Color.BLUE
            val fontTopY = baselineY + textPaint.fontMetrics.top
            canvas.drawLine(lineStartX, fontTopY, lineStopX, fontTopY, linePaint)
            val topTextStartX = (lineStartX - DrawUtil.getTextWidth("top", lineTextPaint)) / 2
            canvas.drawText("top", topTextStartX, fontTopY, lineTextPaint)
            canvas.drawText(
                textPaint.fontMetrics.top.toString(), // top 是相对于 baseline 的，是负值
                distanceStartX,
                fontTopY,
                lineTextPaint
            )
        }

        // Draw ascent
        if (showAscentLine) {
            linePaint.color = Color.GREEN
            val fontAscentY = baselineY + textPaint.ascent()
            canvas.drawLine(lineStartX, fontAscentY, lineStopX, fontAscentY, linePaint)
            val ascentTextStartX = (lineStartX - DrawUtil.getTextWidth("ascent", lineTextPaint)) / 2
            canvas.drawText("ascent", ascentTextStartX, fontAscentY, lineTextPaint)
            canvas.drawText(
                textPaint.ascent().toString(), // ascent 是相对于 baseline 的，是负值
                distanceStartX,
                fontAscentY,
                lineTextPaint
            )
        }

        // Draw baseline
        if (showBaseline) {
            linePaint.color = Color.RED
            canvas.drawLine(lineStartX, baselineY, lineStopX, baselineY, linePaint)
            val baselineTextStartX =
                (lineStartX - DrawUtil.getTextWidth("baseline", lineTextPaint)) / 2
            canvas.drawText("baseline", baselineTextStartX, baselineY, lineTextPaint)
            canvas.drawText("0", distanceStartX, baselineY, lineTextPaint)
        }

        // Draw descent
        if (showDescentLine) {
            linePaint.color = Color.GREEN
            val fontDescentY = baselineY + textPaint.descent()
            canvas.drawLine(lineStartX, fontDescentY, lineStopX, fontDescentY, linePaint)
            val descentTextStartX =
                (lineStartX - DrawUtil.getTextWidth("descent", lineTextPaint)) / 2
            canvas.drawText("descent", descentTextStartX, fontDescentY, lineTextPaint)
            canvas.drawText(
                textPaint.descent().toString(),
                distanceStartX,
                fontDescentY,
                lineTextPaint
            )
        }

        // Draw bottom
        if (showBottomLine) {
            linePaint.color = Color.BLUE
            val fontBottomY = baselineY + textPaint.fontMetrics.bottom
            canvas.drawLine(lineStartX, fontBottomY, lineStopX, fontBottomY, linePaint)
            val bottomTextStartX = (lineStartX - DrawUtil.getTextWidth("bottom", lineTextPaint)) / 2
            canvas.drawText("bottom", bottomTextStartX, fontBottomY + 20, lineTextPaint)
            canvas.drawText(
                textPaint.fontMetrics.bottom.toString(),
                distanceStartX,
                fontBottomY + 20,
                lineTextPaint
            )
        }
    }
}