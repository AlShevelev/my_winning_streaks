package com.shevelev.mywinningstreaks.screens.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

internal class SettingsScreenViewModel(
    private val settingsRepository: SettingsRepository,
    private val diagramUseCase: DiagramUseCase,
) : ViewModel() {
    private var updateJob: Job? = null

    private val _state = MutableStateFlow<SettingsScreenState>(SettingsScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val newState = SettingsScreenState.Data(
                recentDaysToShowValues = listOf(7, 10, 14, 30, 60, 90, 180, 365),
                recentDaysToShow = settingsRepository.getDaysToShow(),
                timeToStart = settingsRepository.getTimeToStart(),
                howOften = settingsRepository.getHowOften(),
                howOftenValues = listOf(5, 10, 15),
                howManyTimes = settingsRepository.getHowManyTimes(),
                howManyTimesValues = listOf(1, 2, 3),
            )

            _state.emit(newState)
        }
    }

    fun setRecentDaysToShow(value: Int) {
        updateJob?.cancel()

        updateJob = viewModelScope.launch {
            settingsRepository.setDaysToShow(value)

            if (!isActive) return@launch

            (_state.value as? SettingsScreenState.Data)
                ?.copy(recentDaysToShow = value)
                ?.let { _state.emit(it) }

            if (!isActive) return@launch

            diagramUseCase.init()
        }
    }

    fun setTimeToStart(value: LocalTime) {
        viewModelScope.launch {
            settingsRepository.setTimeToStart(value)

            (_state.value as? SettingsScreenState.Data)
                ?.copy(timeToStart = value)
                ?.let { _state.emit(it) }
        }
    }

    fun setHowOften(value: Int) {
        viewModelScope.launch {
            settingsRepository.setHowOften(value)

            (_state.value as? SettingsScreenState.Data)
                ?.copy(howOften = value)
                ?.let { _state.emit(it) }
        }
    }

    fun setHowManyTimes(value: Int) {
        viewModelScope.launch {
            settingsRepository.setHowManyTimes(value)

            (_state.value as? SettingsScreenState.Data)
                ?.copy(howManyTimes = value)
                ?.let { _state.emit(it) }

        }
    }
}