package org.example.charts.calendar.utils

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

fun getDaysInMonth(year: Int, month: Int): Int = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    2 -> if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28
    else -> throw IllegalArgumentException("Invalid month")
}

fun getStartDayOfWeek(year: Int, month: Int): Int {
    val date = LocalDate(year, month, 1)
    return when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
        DayOfWeek.SUNDAY -> 7
        else -> throw IllegalArgumentException("Invalid day of week")
    }
}