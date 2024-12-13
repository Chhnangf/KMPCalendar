package org.example.charts.calendar.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.number
import org.example.charts.calendar.data.CalendarViewModel
import org.example.charts.calendar.utils.getDaysInMonth
import org.example.charts.calendar.utils.getStartDayOfWeek

@Composable
fun CalendarView() {

    val vm: CalendarViewModel = CalendarViewModel()


    val calendarState by vm.calendarState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val initialCount = 50
    val pagerState = rememberPagerState(initialPage = initialCount, pageCount = { 120 })
    var count = 50
    Surface {
        Row(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxHeight().weight(0.6f)) {
                Text("currentDate=${calendarState.targetDate}, selectedDate=${calendarState.selectedDate}")

                Row {
                    Button(onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }

                    }) { Text("Previous") }

                    Button(onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }

                    }) { Text("Next") }

                }

                LaunchedEffect(key1 = pagerState.currentPage) {
                    val offset = pagerState.currentPage - count // 假设初始页面为第50页

                    // 创建一个基于默认LocalDate的新LocalDate实例，并根据偏移量调整月份
                    vm.updateDate(offset)

                    count = pagerState.currentPage
                }

                HorizontalPager(state = pagerState) { pageIndex ->
                    Column() {
                        Calendar(vm)
                    }

                }

            }

            LazyColumn(Modifier.fillMaxHeight().weight(0.4f)) {
                itemsIndexed(calendarState.biorhythmState) { index, item ->
                    Text("$index- $item")
                }


            }

        }


    }
}


@Composable
fun Calendar(vm: CalendarViewModel) {

    val calendarState by vm.calendarState.collectAsState()
    val daysOfMonth = getDaysInMonth(calendarState.selectedDate.year, calendarState.selectedDate.month.number)
    val startDayOfWeek = getStartDayOfWeek(calendarState.selectedDate.year, calendarState.selectedDate.month.number)

    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day)
            }
        }

    }

    // 计算总行数
    val rows = (daysOfMonth + startDayOfWeek - 1 + 6) / 7 // 向上取整到最接近的7的倍数
    // 用于计算当月要显示的天数，从1开始
    var index = 1

    // Days of the month
    for (row in 1..rows) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (weekDay in 1..7) {
                if (index > daysOfMonth) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        // Empty space
                    }
                } else if (row == 1 && weekDay < startDayOfWeek) {
                    Box(
                        modifier = Modifier.weight(1f).border(1.dp, Color.Green),
                        contentAlignment = Alignment.Center
                    ) {}
                } else Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Day(vm, index)
                    index++
                }
            }
        }
        if (row < rows) {
            // Spacer(modifier = Modifier.height(2.dp)) // Space between rows
        }
    }
}