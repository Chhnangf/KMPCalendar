package org.example.charts.charts.internal.linechart

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.example.charts.charts.ChartErrors
import org.example.charts.charts.internal.NO_SELECTION
import org.example.charts.charts.internal.TestTags
import org.example.charts.charts.internal.common.model.MultiChartData
import org.example.charts.charts.internal.common.model.composable.ChartView
import org.example.charts.charts.internal.linechart.LineChart
import org.example.charts.charts.internal.validateLineData
import org.example.charts.charts.style.ChartViewStyle
import org.example.charts.charts.style.LineChartDefaults
import org.example.charts.charts.style.LineChartStyle

@Composable
internal fun LineChartViewImpl(
    data: MultiChartData,
    style: LineChartStyle = LineChartDefaults.style(),
) {
    val errors by remember {
        mutableStateOf(
            validateLineData(
                data = data,
                style = style
            )
        )
    }

    if (errors.isEmpty()) {
        var title by remember { mutableStateOf(data.title) }
        var labels by remember { mutableStateOf(listOf<String>()) }

        val lineColors by remember {
            mutableStateOf(
                if (data.hasSingleItem()) {
                    listOf(style.lineColor)
                } else if (style.lineColors.isEmpty()) {
                    generateColorShades(style.lineColor, data.items.size)
                } else {
                    style.lineColors
                }
            )
        }
        ChartView(chartViewsStyle = style.chartViewStyle) {
            Text(
                modifier = style.chartViewStyle.modifierTopTitle
                    .testTag(org.example.charts.charts.internal.TestTags.CHART_TITLE),
                text = title,
                //style = style.chartViewStyle.styleTitle
            )

            LineChart(
                data = data,
                style = style,
                colors = lineColors
            ) { selectedIndex ->
                //title = data.getLabel(selectedIndex)

                if (data.hasCategories()) {
                    labels = when (selectedIndex) {
                        org.example.charts.charts.internal.NO_SELECTION -> emptyList()
                        else -> data.items.map { it.item.labels[selectedIndex] }
                    }
                }
            }

            if (data.hasCategories()) {
                LegendItem(
                    chartViewsStyle = style.chartViewStyle,
                    legend = data.items.map { it.label },
                    colors = lineColors,
                    labels = labels
                )
            }
        }
    } else {
        ChartErrors(style.chartViewStyle, errors)
    }
}

internal fun generateColorShades(baseColor: Color, numberOfShades: Int): List<Color> {
    val step = 0.6f / (numberOfShades)

    return (0 until numberOfShades).map { i ->
        val luminance = step * i
        baseColor.copy(
            red = (baseColor.red * (1 - luminance) + luminance).coerceIn(0f, 1f),
            green = (baseColor.green * (1 - luminance) + luminance).coerceIn(0f, 1f),
            blue = (baseColor.blue * (1 - luminance) + luminance).coerceIn(0f, 1f)
        )
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun LegendItem(
    chartViewsStyle: ChartViewStyle,
    legend: List<String>,
    colors: List<Color>,
    labels: List<String> = emptyList()
) {
    FlowRow(
        modifier = chartViewsStyle.modifierLegend.animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        legend.forEachIndexed { index, legend ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .background(colors[index])
                )

                val label = when (labels.isEmpty()) {
                    true -> legend
                    else -> "$legend - ${labels[index]}"
                }

                Text(
                    text = label,
                    color = colors[index],
                    modifier = Modifier.padding(
                        start = chartViewsStyle.innerPadding,
                        end = chartViewsStyle.innerPadding
                    )
                )
            }
        }
    }
}
