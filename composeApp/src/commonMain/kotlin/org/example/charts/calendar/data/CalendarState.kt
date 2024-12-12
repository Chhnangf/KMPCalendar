package org.example.charts.calendar.data

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class CalendarState(
    val targetDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),  // 当前显示的月份
    val selectedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()), // 用户选择的具体日期,默认为今日
    val biorhythmState: List<BiorhythmState> = emptyList(),    // 当前月份的生物节律
)