//package org.example.charts.charts
//
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.platform.testTag
//import org.example.charts.charts.model.ChartDataSet
//
//
///**
// * A composable function that displays a Pie Chart.
// *
// * @param dataSet The data set to be displayed in the chart.
// * @param style The style to be applied to the chart. If not provided, the default style will be used.
// */
//@Composable
//fun PieChartView(
//    dataSet: ChartDataSet,
//    style: PieChartStyle = PieChartDefaults.style(),
//) {
//    style.pieColors = style.pieColors.ifEmpty {
//        generateColorShades(style.pieColor, dataSet.data.item.points.size)
//    }
//
//    val errors by remember {
//        mutableStateOf(validatePieData(dataSet = dataSet, style = style))
//    }
//
//    if (errors.isNotEmpty()) {
//        ChartErrors(chartViewStyle = style.chartViewStyle, errors = errors)
//    } else {
//        ChartContent(dataSet = dataSet, style = style)
//    }
//}
//
//@Composable
//private fun ChartContent(
//    dataSet: ChartDataSet,
//    style: PieChartStyle
//) {
//    var title by remember { mutableStateOf(dataSet.data.label) }
//
//    ChartView(chartViewsStyle = style.chartViewStyle) {
//        Text(
//            modifier = style.chartViewStyle.modifierTopTitle
//                .testTag(TestTags.CHART_TITLE),
//            text = title,
//            //style = style.chartViewStyle.styleTitle
//        )
//        PieChart(
//            chartData = dataSet.data.item,
//            style = style,
//            chartStyle = style.chartViewStyle
//        ) {
//            title = when (it) {
//                NO_SELECTION -> dataSet.data.label
//                else -> dataSet.data.item.labels[it]
//            }
//        }
//    }
//}
