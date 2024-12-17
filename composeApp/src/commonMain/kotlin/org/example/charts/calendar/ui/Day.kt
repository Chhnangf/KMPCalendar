package org.example.charts.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import org.example.charts.calendar.data.CalendarViewModel

@Composable
fun Day(vm: CalendarViewModel, localDate: LocalDate) {
    val nowDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val calendar by vm.calendarState.collectAsState()
    val isToday = calendar.selectedDate.year == nowDate.year &&
            calendar.selectedDate.month.number == nowDate.monthNumber &&
            localDate.dayOfMonth == nowDate.dayOfMonth
    val isSelectedDay =
        calendar.selectedDate.dayOfMonth == localDate.dayOfMonth

    val backgroundColor = if (isSelectedDay) Color.Black else Color.LightGray
    val textColor = if (isSelectedDay) Color.White else Color.Black

    Box(
        modifier = Modifier.width(60.dp).height(40.dp).padding(4.dp)
            .clickable {
                vm.onSelectedDate(localDate)

            }) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                ) {
                    // Action Status
                }
            }
            Row(
                modifier = Modifier.width(40.dp).height(25.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(6.dp))
                        .background(backgroundColor, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (isToday) "今" else localDate.dayOfMonth.toString(), color = textColor)
                }
            }
        }
    }
}


