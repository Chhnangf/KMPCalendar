package org.example.charts.calendar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import org.example.charts.calendar.utils.calculateBiorhythmsForMonth
import org.example.charts.calendar.utils.calculateBiorhythmsForWeek

class CalendarViewModel : ViewModel() {


    private var _calendarState = MutableStateFlow<CalendarState>(CalendarState())

    val calendarState = _calendarState.map { calendar ->
        calendar.copy(
            targetDate = calendar.targetDate,
            selectedDate = calendar.selectedDate,
            biorhythmState =
                if (calendar.model == CalendarModel.WEEKLY) {
                    calculateBiorhythmsForWeek(targetDate = calendar.targetDate)
                } else {
                    calculateBiorhythmsForMonth(targetDate = calendar.targetDate)
                }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _calendarState.value)


    // 提供一个方法来更新日期
    fun updateDate(offset: Int, model: CalendarModel) {
        val newDate = if (model.isWeekly()) {
            _calendarState.value.targetDate.plus(DatePeriod(days = offset * 7))
        } else {
            _calendarState.value.targetDate.plus(DatePeriod(months = offset))
        }

        _calendarState.update { it.copy(targetDate = newDate) }
    }

    fun onSelectedDate(selectedDate: LocalDate) {
        _calendarState.update { it.copy(selectedDate = selectedDate) }
    }

    fun changeModel(model: CalendarModel) {
        viewModelScope.launch {
            if(_calendarState.value.model != model) {
                _calendarState.update { it.copy(model = model) }
            }
        }

    }


}