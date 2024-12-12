package org.example.charts.calendar.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import kotlinx.datetime.*
import org.example.charts.calendar.data.CalendarViewModel
import org.example.charts.calendar.utils.getDaysInMonth
import org.example.charts.calendar.utils.getStartDayOfWeek

@Composable
fun CalendarView() {
    var nowDate by remember { mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault())) }

    val vm: CalendarViewModel = CalendarViewModel()
    val uiState by vm.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val initialCount = 50
    val pagerState = rememberPagerState(initialPage = initialCount, pageCount = { 120 })
    var count = 50
    Surface {
        Column(Modifier.fillMaxHeight()) {

            Text("${uiState.year}-${uiState.month}-${uiState.day}")
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
            WeekTitle()

            LaunchedEffect(key1 = pagerState.currentPage) {
                val offset = pagerState.currentPage - count // 假设初始页面为第50页

                // 创建一个基于当前状态的新日期，并根据偏移量调整月份
                nowDate = nowDate.plus(DatePeriod(months = offset))

                // 更新 ViewModel 中的状态
                vm.updateYearMonth(nowDate.year, nowDate.month.number)

                // 更新 nowDate 以便下一次计算
//                nowDate = newDate

                // 更新 ViewModel 中的状态
                vm.updateYearMonth(nowDate.year, nowDate.month.number)
                count = pagerState.currentPage
            }

            HorizontalPager(state = pagerState) { pageIndex ->
                Column() {
                    Calendar(vm)
                }

            }

        }

    }
}

@Composable
fun WeekTitle() {
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier.weight(1f).border(1.dp, Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day)
            }
        }

    }
}

@Composable
fun Calendar(vm: CalendarViewModel) {

    val uiState by vm.uiState.collectAsState()
    val daysOfMonth = getDaysInMonth(uiState.year, uiState.month)
    val startDayOfWeek = getStartDayOfWeek(uiState.year, uiState.month)

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