package org.example.charts.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.example.charts.calendar.data.CalendarViewModel

@Composable
fun Day(vm: CalendarViewModel,index: Int) {

    val uiState by vm.uiState.collectAsState()
    val nowDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val isToday = uiState.year == nowDate.year &&
            uiState.month == nowDate.monthNumber &&
           index == nowDate.dayOfMonth

    val isSelectedDay = uiState.day == index
    val backgroundColor = if (isSelectedDay) Color.Black else Color.LightGray
    val textColor = if (isSelectedDay) Color.White else Color.Black

    Box(
        modifier = Modifier.width(60.dp).height(40.dp).padding(4.dp)
            .clickable { vm.selectDateOfDay(uiState.year,uiState.month,index) }) {
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
                        .background(backgroundColor,RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (isToday) "今" else index.toString(), color = textColor)
                }
            }
        }
    }
}


