package org.example.charts.calendar.data

import kotlinx.datetime.LocalDate

data class BiorhythmState(
    val localDate: LocalDate,
    val physical: Int,
    val emotional: Int,
    val intellectual: Int
)