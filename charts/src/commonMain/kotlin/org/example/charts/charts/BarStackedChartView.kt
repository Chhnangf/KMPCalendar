package org.example.charts.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.testTag
import org.example.charts.charts.internal.common.model.validateBarData
import org.example.charts.charts.model.MultiChartDataSet
import org.example.charts.charts.style.StackedBarChartDefaults
import org.example.charts.charts.style.StackedBarChartStyle


/**
 * A composable function that displays a Stacked Bar Chart.
 *
 * @param dataSet The data set to be displayed in the chart.
 * @param style The style to be applied to the chart. If not provided, the default style will be used.
 */
@Composable
fun StackedBarChartView(
    dataSet: MultiChartDataSet,
    style: StackedBarChartStyle = StackedBarChartDefaults.style()
) {
    val errors by remember {
        mutableStateOf(
            validateBarData(
                data = dataSet.data,
                style = style
            )
        )
    }

    if (errors.isEmpty()) {
        ChartContent(dataSet = dataSet, style = style)
    } else {
        ChartErrors(chartViewStyle = style.chartViewStyle, errors = errors)
    }
}

@Composable
private fun ChartContent(dataSet: MultiChartDataSet, style: StackedBarChartStyle) {
    var title by remember { mutableStateOf(dataSet.data.title) }
    var labels by remember { mutableStateOf(listOf<String>()) }

//    val colors by remember {
//        mutableStateOf(
//            style.barColors.ifEmpty {
//                generateColorShades(style.barColor, dataSet.data.getFirstPointsSize())
//            }
//        )
//    }
//
//    ChartView(chartViewsStyle = style.chartViewStyle) {
//        Text(
//            modifier = style.chartViewStyle.modifierTopTitle
//                .testTag(TestTags.CHART_TITLE),
//            text = title,
//            //style = style.chartViewStyle.styleTitle
//        )
//
//        StackedBarChart(
//            data = dataSet.data,
//            style = style,
//            colors = colors
//        ) { selectedIndex ->
//            title = when (selectedIndex) {
//                NO_SELECTION -> dataSet.data.title
//                else -> {
//                    dataSet.data.items[selectedIndex].label
//                }
//            }
//
//            if (dataSet.data.hasCategories()) {
//                labels = when (selectedIndex) {
//                    NO_SELECTION -> emptyList()
//                    else -> dataSet.data.items[selectedIndex].item.labels
//                }
//            }
//        }
//
//        if (dataSet.data.hasCategories()) {
//            LegendItem(
//                chartViewsStyle = style.chartViewStyle,
//                colors = colors,
//                legend = dataSet.data.categories,
//                labels = labels
//            )
//        }
//    }
}
