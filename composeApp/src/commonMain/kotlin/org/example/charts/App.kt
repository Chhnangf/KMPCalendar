package org.example.charts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.charts.calendar.data.CalendarModel
import org.example.charts.calendar.data.CalendarViewModel
import org.example.charts.calendar.ui.BarView
import org.example.charts.calendar.ui.CalendarView
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.charts.calendar.ui.CustomTabRow
import org.example.charts.charts.BarChartView
import org.example.charts.charts.model.ChartDataSet
import org.example.charts.charts.style.BarChartDefaults

@Composable
@Preview
fun App() {
    val vm: CalendarViewModel = CalendarViewModel()

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val calendar by vm.calendarState.collectAsState()


        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(colors = ButtonDefaults.buttonColors(Color.Transparent),onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    val itemsName = listOf("e", "i", "p")
                    var tabSelected by remember { mutableStateOf(1) }
                    CustomTabRow(
                        tabSelected = tabSelected,
                        onTabSelected = { newIndex -> tabSelected = newIndex },
                        itemsName = itemsName
                    ) {}
                    CalendarView(vm)


                    when (tabSelected) {
                        0 -> {
                            BarView(
                                vm,
                                itemsName[0],
                                calendar.biorhythmState.map { it.emotional.toFloat() })
                        }

                        1 -> {
                            BarView(
                                vm,
                                itemsName[1],
                                calendar.biorhythmState.map { it.intellectual.toFloat() })
                        }

                        2 -> {
//                            BarView(
//                                vm,
//                                itemsName[2],
//                                calendar.biorhythmState.map { it.physical.toFloat() })
                            LazyColumn(modifier = Modifier.width(300.dp).height(400.dp)) {
                                itemsIndexed(calendar.biorhythmState) { index, item ->
                                    androidx.compose.material3.Text(item.toString())
                                }
                            }
                        }

                    }
                }

            }
        }
    }
}