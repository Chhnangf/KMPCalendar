package org.example.charts.charts.internal

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec


internal object AnimationSpec {

    private fun duration(
        index: Int,
        duration: Int = org.example.charts.charts.internal.ANIMATION_DURATION,
        offset: Int = org.example.charts.charts.internal.ANIMATION_OFFSET
    ): Int {
        return duration + offset * index
    }

    fun lineChart() = TweenSpec<Float>(
        durationMillis = org.example.charts.charts.internal.ANIMATION_DURATION_LINE,
        delay = 0,
        easing = LinearEasing
    )

    fun barChart(index: Int) = TweenSpec<Float>(
        durationMillis = duration(index = index),
        delay = 0,
        easing = LinearEasing
    )

    fun stackedBar(index: Int) = TweenSpec<Float>(
        durationMillis = duration(
            index = index,
            duration = org.example.charts.charts.internal.ANIMATION_DURATION_BAR
        ),
        delay = 0,
        easing = LinearEasing
    )

    fun pieChart(index: Int) = TweenSpec<Float>(
        durationMillis = duration(index = index),
        delay = index * org.example.charts.charts.internal.ANIMATION_OFFSET,
        easing = LinearEasing
    )

    fun pieChartDonut() = TweenSpec<Float>(
        durationMillis = 900,
        delay = 0
    )
}