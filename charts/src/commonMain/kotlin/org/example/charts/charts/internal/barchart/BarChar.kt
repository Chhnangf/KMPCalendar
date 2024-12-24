package org.example.charts.charts.internal.barchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import org.example.charts.charts.internal.AnimationSpec
import org.example.charts.charts.internal.DEFAULT_SCALE
import org.example.charts.charts.internal.MAX_SCALE
import org.example.charts.charts.internal.NO_SELECTION
import org.example.charts.charts.internal.TestTags
import org.example.charts.charts.internal.common.model.ChartData
import org.example.charts.charts.style.*
import org.example.charts.charts.testTag
import kotlin.math.abs


@OptIn(ExperimentalTextApi::class)
@Composable
internal fun BarChart(
    chartData: ChartData,
    style: BarChartStyle,
    onValueChanged: (Int) -> Unit = {}
) {
    val barColor = style.barColor
    // 使用 rememberUpdatedState 来获取最新的 chartData
    val currentChartData by rememberUpdatedState(chartData)
    var progress by remember {
        mutableStateOf<List<Animatable<Float, AnimationVector1D>>>(chartData.points.map { value ->
            Animatable(0f)
        })
    }

    // 监听 chartData 的变化，并根据需要调整 progress 的长度
    LaunchedEffect(currentChartData) {
        val newPointsSize = currentChartData.points.size
        if (newPointsSize != progress.size) {
            progress = List(newPointsSize) { index ->
                if (index < progress.size) progress[index] else Animatable(0f)
            }
        }

        // 动态更新每个 Animatable 的目标值
        progress.forEachIndexed { index, animatable ->
            launch {
                animatable.snapTo(0f) // 确保从头开始动画
                animatable.animateTo(
                    targetValue = abs(currentChartData.points.getOrNull(index)?.toFloat() ?: 0f),
                    animationSpec = AnimationSpec.barChart(index)
                )
            }
        }
    }

    var selectedIndex by remember { mutableStateOf(org.example.charts.charts.internal.NO_SELECTION) }
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = style.modifier
        .testTag(org.example.charts.charts.internal.TestTags.BAR_CHART)
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, _ ->
                    selectedIndex =
                        getSelectedIndex(
                            position = change.position,
                            dataSize = chartData.points.count(),
                            canvasSize = size
                        )
                    onValueChanged(selectedIndex)
                    change.consume()
                },
                onDragEnd = {
                    selectedIndex = org.example.charts.charts.internal.NO_SELECTION
                    onValueChanged(org.example.charts.charts.internal.NO_SELECTION)
                }
            )
        }, onDraw = {
        drawBars(
            style = style,
            size = size,
            chartData = chartData,
            progress = progress,
            selectedIndex = selectedIndex,
            barColor = barColor,
            textMeasurer
        )
    })


}


@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawBars(
    style: BarChartStyle,
    size: Size,
    chartData: ChartData,
    progress: List<Animatable<Float, AnimationVector1D>>,
    selectedIndex: Int,
    barColor: Color,
    textMeasurer: TextMeasurer
) {
    // 每次绘制时重新计算最大值和最小值
    val maxValue = chartData.points.maxOrNull() ?: 0.0
    val minValue = chartData.points.minOrNull() ?: 0.0

    val baselineY = size.height * (maxValue / (maxValue - minValue))
    val dataSize = chartData.points.size

    val colorStops =  arrayOf(
        0.0f to Color(0xAAC8ADD8),
        1f to Color(0xAAFCBBC3)
    )

    var brush = Brush.verticalGradient(colorStops = colorStops)


    // 绘制X轴刻度线
    drawLine(
        color = Color.Gray, // X轴刻度线的颜色，这里使用灰色作为示例
        start = Offset(x = 0f, y = size.height), // X轴刻度线的起点
        end = Offset(x = size.width, y = size.height), // X轴刻度线的终点
        strokeWidth = 1f // X轴刻度线的宽度
    )

    chartData.points.forEachIndexed { index, value ->
        if (index >= progress.size) return // 防止索引越界

        val spacing = style.space.toPx()
        //val barWidth = (size.width - spacing * (dataSize - 1)) / dataSize
        val barWidth = size.width / dataSize - spacing

        val selectedBarScale = if (index == selectedIndex) org.example.charts.charts.internal.MAX_SCALE else org.example.charts.charts.internal.DEFAULT_SCALE
        val finalBarHeight =
            size.height * selectedBarScale * (abs(value) / (maxValue - minValue)) / 100

        val barHeight =
            lerp(0f, finalBarHeight.toFloat(), progress[index].value)

        val top = if (value >= 0) baselineY - barHeight else baselineY
        val left = (barWidth + spacing) * index- 0.1f

        drawRect(
            brush = brush,
            topLeft = Offset(x = left, y = top.toFloat()),
            size = Size(
                width = barWidth * selectedBarScale,
                height = barHeight
            )
        )
        // 临界值日期提醒
        drawLine(
            color = if (value.toInt() == 0) Color.Red else Color.Transparent,
            start = Offset(x = left, y = baselineY.toFloat()),
            end = Offset(x = left+barWidth, y = baselineY.toFloat()),
            alpha = 1f // 不透明度
        )

        // x轴详细刻度
        drawLine(
            color = Color.Gray,
            start = Offset(x = left+barWidth, y = size.height),
            end = Offset(x = left+barWidth, y = size.height - 5),
        )
        // 动态计算文本大小
        val dynamicTextSize =lerp(MIN_CHART_TEXT_SIZE, MAX_CHART_TEXT_SIZE,
            (barWidth - MIN_CHART_BAR_WIDTH) / (MAX_CHART_BAR_WIDTH - MIN_CHART_BAR_WIDTH))
        val text = "${index + 1}" // 要绘制的文本
        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(fontSize = dynamicTextSize.sp),
        )

        drawText(
            textLayoutResult = textLayoutResult,
            color = Color.Black, // 使用条形相同的颜色
            topLeft = Offset(x = left + barWidth / 2, y = size.height), // 居中对齐文本
            alpha = 1f // 不透明度
        )

    }

}

internal fun getSelectedIndex(position: Offset, dataSize: Int, canvasSize: IntSize): Int {
    val barWidth = canvasSize.width / dataSize
    val index = (position.x / (barWidth)).toInt()
    return index.coerceIn(0, dataSize - 1)
}
