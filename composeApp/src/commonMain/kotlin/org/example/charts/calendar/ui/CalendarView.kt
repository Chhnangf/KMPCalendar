package org.example.charts.calendar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import org.example.charts.calendar.data.CalendarModel
import org.example.charts.calendar.data.CalendarState
import org.example.charts.calendar.data.CalendarViewModel
import org.example.charts.calendar.utils.getTotalDaysInMonth
import org.example.charts.calendar.utils.getDayOfWeek

@Composable
fun CalendarView(vm: CalendarViewModel) {

    val calendarState by vm.calendarState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val initialCount = 50
    val pagerState = rememberPagerState(initialPage = initialCount, pageCount = { 120 })
    var count = 50

    var model by remember { mutableStateOf(CalendarModel.WEEKLY) }

    Surface {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier) {
                Text("currentDate=${calendarState.targetDate}, selectedDate=${calendarState.selectedDate}")

                Row {
                    Button(colors = ButtonDefaults.buttonColors(Color.Transparent), onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }

                    }) { Text("Previous") }

                    Button(colors = ButtonDefaults.buttonColors(Color.Transparent), onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }

                    }) { Text("Next") }

                    Button(colors = ButtonDefaults.buttonColors(Color.Transparent), onClick = {
                        if (model == CalendarModel.WEEKLY) {
                            model = CalendarModel.MONTHLY
                        } else
                            model = CalendarModel.WEEKLY
                        vm.changeModel(model)

                    }) { Text(model.toString()) }

                }

                LaunchedEffect(key1 = pagerState.currentPage) {
                    val offset = pagerState.currentPage - count // 假设初始页面为第50页

                    // 创建一个基于默认LocalDate的新LocalDate实例，并根据偏移量调整月份
                    vm.updateDate(offset, model)

                    count = pagerState.currentPage
                }

                HorizontalPager(state = pagerState) { pageIndex ->
                    Column() {
                        Calendar(vm, model)
                    }

                }

            }


        }


    }
}


@Composable
fun Calendar(vm: CalendarViewModel, model: CalendarModel = CalendarModel.WEEKLY) {

    val calendarState by vm.calendarState.collectAsState()

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

    when (model) {
        CalendarModel.MONTHLY -> MonthlyCalendar(vm, calendarState)
        CalendarModel.WEEKLY -> WeeklyCalendar(vm, calendarState)
    }


}


@Composable
private fun MonthlyCalendar(vm: CalendarViewModel, state: CalendarState) {
    // 这里是你的原有月历UI代码
    // 你可以直接将原来的Calendar函数内容移到这里
    // 略...
    val daysOfMonth = getTotalDaysInMonth(state.targetDate)
    val startDayOfWeek = getDayOfWeek(state.targetDate, 1)

    val rows = (daysOfMonth + startDayOfWeek - 1 + 6) / 7 // 向上取整到最接近的7的倍数
    // 用于计算当月要显示的天数，从1开始
    var index = 1
    // Days of the month
    for (row in 1..rows) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (weekDay in 1..7) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    if (index > daysOfMonth) {
                    } else if (row == 1 && weekDay < startDayOfWeek) {
                    } else {
                        Day(vm, LocalDate(state.targetDate.year, state.targetDate.month, index))
                        index++

                    }
                }
            }
        }

    }
}


@Composable
private fun WeeklyCalendar(vm: CalendarViewModel, calendarState: CalendarState) {
    val dayOfWeek = calendarState.targetDate.dayOfWeek
    val monday = calendarState.targetDate.minus(DatePeriod(days = dayOfWeek.ordinal))
    // 使用list容器存放要显示的日历集合
    val calendar = (0..6).map { monday.plus(DatePeriod(days = it)) }
    Row(modifier = Modifier.fillMaxWidth()) {
        calendar.forEachIndexed { index, localDate ->
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Day(vm, localDate)
            }
        }
    }
}

@Composable
private fun MonthlyCalendar2(vm: CalendarViewModel, state: CalendarState) {

    val daysOfMonth = getTotalDaysInMonth(state.targetDate)
    val startDayOfWeek = getDayOfWeek(state.targetDate, 1)
    val lastWeek = LocalDate(state.targetDate.year, state.targetDate.month, 1).minus(DatePeriod(days = startDayOfWeek))
    val lastMonthDays = getTotalDaysInMonth(state.targetDate.minus(DatePeriod(months = 1)))


    val dayOfMonth = state.targetDate.dayOfMonth
    val firstDayOfMonth = state.targetDate.minus(DatePeriod(days = dayOfMonth))
    val calendar = (0..daysOfMonth).map { firstDayOfMonth.plus(DatePeriod(days = it)) }

    // targetDate日历需要的行数，每行7天
    val rows = (daysOfMonth + startDayOfWeek - 1 + 6) / 7 // 向上取整到最接近的7的倍数


}