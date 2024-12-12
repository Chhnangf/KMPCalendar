package org.example.charts.calendar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import org.example.charts.calendar.utils.calculateBiorhythmsForMonth

class CalendarViewModel: ViewModel() {


    var _calendarState = MutableStateFlow<CalendarState>(CalendarState())

    val calendarState = _calendarState.map { calendar ->
        calendar.copy(calendar.targetDate,biorhythmState = calculateBiorhythmsForMonth(targetDate = calendar.targetDate)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _calendarState.value)



    // 提供一个方法来更新日期
    fun updateDate(offset: Int) {
        val newDate = _calendarState.value.targetDate.plus(DatePeriod(months = offset))
        _calendarState.update { it.copy(targetDate = newDate) }
    }

    fun onSelectedDate(selectedDate: LocalDate) {
        _calendarState.update { it.copy(selectedDate = selectedDate) }
    }




}