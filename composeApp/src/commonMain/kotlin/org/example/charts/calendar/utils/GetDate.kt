package org.example.charts.calendar.utils

import kotlinx.datetime.*
import org.example.charts.calendar.data.BiorhythmState
import kotlin.math.PI
import kotlin.math.sin

fun getTotalDaysInMonth(localData: LocalDate): Int = when (localData.month.number) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    2 -> if ((localData.year % 4 == 0 && localData.year % 100 != 0) || localData.year % 400 == 0) 29 else 28
    else -> throw IllegalArgumentException("Invalid month")
}

/**
 * Return targetDate.DayOfWeek.Index
 */
fun getDayOfWeek(localData: LocalDate, targetDate: Int): Int {
    val date = LocalDate(localData.year, localData.month, targetDate)
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

fun LocalDate.with(DayOfWeek: DayOfWeek): LocalDate {
    val daysUntilTargetDay = DayOfWeek.ordinal - this.dayOfWeek.ordinal
    return this.plus(daysUntilTargetDay.coerceIn(-6..6).toLong(), DateTimeUnit.DAY)
}



fun calculateBiorhythmsForMonth(birthDate: LocalDate = LocalDate(2000,10,28), targetDate: LocalDate): List<BiorhythmState> {
    val startOfMonth = LocalDate(targetDate.year, targetDate.month, 1)
    val lastDayOfMonth = startOfMonth.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)

    return (startOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth).map { day ->
        val targetDay = LocalDate(targetDate.year, targetDate.month, day)
        calculateBiorhythms(birthDate, targetDay)
    }
}

fun calculateBiorhythmsForWeek(birthDate: LocalDate = LocalDate(2000,5,23), targetDate: LocalDate): List<BiorhythmState> {
    return (-7..7).map { day ->
        val targetDay = targetDate.plus(DatePeriod(days = day))
        calculateBiorhythms(birthDate, targetDay)
    }
}

fun calculateBiorhythms(birthDate: LocalDate, targetDate: LocalDate): BiorhythmState {
    // 计算出生日至目标日期的总天数
    val totalDays = birthDate.daysUntil(targetDate)
    // 计算每个周期的余数
    val physical = BiorhythmRatio(totalDays, PHYSICAL_PERIOD)
    val emotional = BiorhythmRatio(totalDays, EMOTIONAL_PERIOD)
    val intellectual = BiorhythmRatio(totalDays, INTELLECTUAL_PERIOD)
    return BiorhythmState(targetDate, physical, emotional, intellectual)

}

fun BiorhythmRatio(totalDays: Int, period: Int): Int {
    val biorhythmValue = sin((2 * PI * totalDays) / period)
    return (biorhythmValue * 100).toInt()
}

