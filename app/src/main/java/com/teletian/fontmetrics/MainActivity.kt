package com.teletian.fontmetrics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // top
        font_metrics_view.showTopLine = top.isChecked
        top.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showTopLine = isChecked
        }

        // ascent
        font_metrics_view.showAscentLine = top.isChecked
        ascent.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showAscentLine = isChecked
        }

        // baseline
        font_metrics_view.showBaseline = top.isChecked
        baseline.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showBaseline = isChecked
        }

        // descent
        font_metrics_view.showDescentLine = top.isChecked
        descent.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showDescentLine = isChecked
        }

        // bottom
        font_metrics_view.showBottomLine = top.isChecked
        bottom.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showBottomLine = isChecked
        }

        /*
         * text bounds
         *      getTextBounds 计算的正好是文字的四周
         *      measureText 计算的文字长度，比 getTextBounds 长一点，也就是文字左右两边有一点空隙
         *      getTextBounds 能计算文字四周，而 measureText 只能计算文字长度
         */

        // text bounds (use measureText method)
        font_metrics_view.showTextBoundsLineByMeasureText = text_bounds_measureText.isChecked
        text_bounds_measureText.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showTextBoundsLineByMeasureText = isChecked
            font_metrics_view.showTextBoundsLineByGetTextBounds = !isChecked
        }

        // text bounds (use getTextBounds method)
        font_metrics_view.showTextBoundsLineByGetTextBounds = text_bounds_getTextBounds.isChecked
        text_bounds_getTextBounds.setOnCheckedChangeListener { _, isChecked ->
            font_metrics_view.showTextBoundsLineByGetTextBounds = isChecked
            font_metrics_view.showTextBoundsLineByMeasureText = !isChecked
        }
    }
}
