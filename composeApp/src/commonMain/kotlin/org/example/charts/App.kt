package org.example.charts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.charts.calendar.ui.CalendarView
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.charts.calendar.ui.CustomTabRow
import org.example.charts.charts.BarChartView
import org.example.charts.charts.model.ChartDataSet
import org.example.charts.charts.style.BarChartDefaults

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    var tabSelected by remember { mutableStateOf(1) }
                    val itemsName = listOf("e", "i", "p")
                    Row {
                        CustomTabRow(
                            tabSelected = tabSelected,
                            onTabSelected = { newIndex -> tabSelected = newIndex },
                            itemsName = itemsName
                        ) {}
                    }

                    when (tabSelected) {
                        0 -> {
                            // 这里可以放置你的 BarChartView 组件
                            val items: List<Float> = listOf(100f, -60f, 0f, -90f, 40f, 80f)
                            Box(modifier = Modifier.width(400.dp).height(200.dp)) {
                                BarChartView(
                                    dataSet = ChartDataSet(
                                        items = items,
                                        title = "Emotional",
                                    ),
                                    style = BarChartDefaults.style(
                                        space = 2.dp,
                                    )
                                )
                            }
                        }

                        1 -> {
                            Text(text = "Intellectual")
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CalendarView()
                            }
                        }

                        2 -> {
                            Text(text = "Physical")
                        }
                    }
                }

            }
        }
    }
}