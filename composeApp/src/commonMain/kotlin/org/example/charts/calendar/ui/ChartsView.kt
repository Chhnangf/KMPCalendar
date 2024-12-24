package org.example.charts.calendar.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.charts.calendar.data.CalendarViewModel
import org.example.charts.charts.BarChartView
import org.example.charts.charts.LineChartView
import org.example.charts.charts.model.ChartDataSet
import org.example.charts.charts.style.BarChartDefaults
import org.example.charts.charts.style.BarChartStyle
import org.example.charts.charts.style.LineChartDefaults

@Composable
fun BarView(vm: CalendarViewModel,title:String, items: List<Float>,style: BarChartStyle = BarChartDefaults. style()) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = title, fontSize = 24.sp)
        Box(modifier = Modifier.width(400.dp).height(200.dp)) {
            BarChartView(
                dataSet = ChartDataSet(
                    items = items,
                    title = title,
                ),
                style = BarChartDefaults.style(
                    space = 2.dp,
                )
            )
        }
    }

}

@Composable
fun LineView(vm: CalendarViewModel,title:String, items: List<Float>,style: BarChartStyle = BarChartDefaults. style()) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = title, fontSize = 24.sp)
        Box(modifier = Modifier.width(400.dp).height(200.dp)) {
            LineChartView(
                dataSet = ChartDataSet(
                    items = items,
                    title = title,
                ),
                style = LineChartDefaults.style()
            )
        }
    }

}