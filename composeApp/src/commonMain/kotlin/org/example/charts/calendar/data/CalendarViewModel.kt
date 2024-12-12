package org.example.charts.calendar.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.*

class CalendarViewModel: ViewModel() {

    private var newDate = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    private val _uiState = MutableStateFlow(CalendarState(
        year = newDate.value.year, month = newDate.value.month.number, day = newDate.value.dayOfMonth,
    ))

    val uiState: StateFlow<CalendarState> = _uiState.asStateFlow()

    fun updateYearMonth(year: Int, month: Int) {
        _uiState.update { it.copy(year = year, month = month) }
    }

    fun selectDateOfDay(year: Int, month: Int, day: Int) {
        _uiState.update { newCalendarState ->
            newCalendarState.copy(
                year = year,
                month = month,
                day = day,
            )
        }
    }

}