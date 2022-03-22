package com.example.custom_clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.time.OffsetTime
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
class AnalogClock constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var backgroundPaint: Paint = Paint()
    private var hourPaint: Paint = Paint()
    private var minutePaint: Paint = Paint()
    private var secondPaint: Paint = Paint()
    private var time: OffsetTime

    init {
        time = OffsetTime.now()
        backgroundPaint.apply {
            isAntiAlias = true
            color = Color.YELLOW
            style = Paint.Style.FILL
        }

        hourPaint.apply {
            isAntiAlias = true
            color = Color.BLUE
            strokeWidth = 10f
        }

        minutePaint.apply {
            isAntiAlias = true
            color = Color.RED
            strokeWidth = 10f
        }

        secondPaint.apply {
            isAntiAlias = true
            color = Color.WHITE
            strokeWidth = 10f
        }

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                time = OffsetTime.now()
                postInvalidate()
            }
        }, 0, 1000L) //5 se

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        // draw circle background
        canvas?.drawCircle(centerX, centerY, centerX, backgroundPaint)

        // draw hour 360/12 = 30
        val minHourX = centerX + (width * 0.25 * cos(time.hour * 30 * Math.PI / 180))
        val minHourY = centerY + (width * 0.25 * sin(time.hour * 30 * Math.PI / 180))
        canvas?.drawLine(centerX, centerY, minHourX.toFloat(), minHourY.toFloat(), hourPaint)

        // draw minutes 360/60 = 6
        val minMinuteX = centerX + (width * 0.27 * cos(time.minute * 6 * Math.PI / 180))
        val minMinuteY = centerY + (width * 0.27 * sin(time.minute * 6 * Math.PI / 180))
        canvas?.drawLine(centerX, centerY, minMinuteX.toFloat(), minMinuteY.toFloat(), minutePaint)

        // draw second 360/60 = 6
        val minSecondX = centerX + (width * 0.3 * cos(time.second * 6 * Math.PI / 180))
        val minSecondY = centerY + (width * 0.3 * sin(time.second * 6 * Math.PI / 180))
        canvas?.drawLine(centerX, centerY, minSecondX.toFloat(), minSecondY.toFloat(), secondPaint)
    }

}