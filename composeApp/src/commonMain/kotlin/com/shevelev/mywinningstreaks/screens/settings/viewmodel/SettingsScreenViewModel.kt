package com.shevelev.mywinningstreaks.screens.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class SettingsScreenViewModel(
    private val settingsRepository: SettingsRepository,
    private val diagramUseCase: DiagramUseCase,
) : ViewModel() {
    private var updateJob: Job? = null

    private val _state = MutableStateFlow(SettingsScreenState(
        possibleRecentDaysToShow = listOf(7, 10, 14, 30, 60, 90, 180, 365),
        selectedRecentDaysToShow = settingsRepository.defaultDaysToShow,
    ))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(selectedRecentDaysToShow = settingsRepository.getDaysToShow())
            }
        }
    }

    fun setSelectedRecentDaysToShow(value: Int) {
        updateJob?.cancel()

        updateJob = viewModelScope.launch {
            settingsRepository.setDaysToShow(value)

            if (!isActive) return@launch

            _state.update {
                it.copy(selectedRecentDaysToShow = value)
            }

            if (!isActive) return@launch

            diagramUseCase.init()
        }
    }
}