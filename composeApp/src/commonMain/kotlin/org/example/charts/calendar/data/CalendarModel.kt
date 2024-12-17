package org.example.charts.calendar.data

enum class CalendarModel {
    WEEKLY, // 表示周历模式
    MONTHLY; // 表示月历模式

    fun isWeekly() = this == WEEKLY
    fun isMonthly() = this == MONTHLY
}