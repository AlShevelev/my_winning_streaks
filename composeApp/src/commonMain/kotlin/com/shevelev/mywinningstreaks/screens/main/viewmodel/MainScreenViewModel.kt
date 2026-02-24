package com.shevelev.mywinningstreaks.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.mywinningstreaks.shared.alarms.AlarmsManagement
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MainScreenViewModel(
    private val useCase: DiagramUseCase,
    private val alarmsManagement: AlarmsManagement,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _managedState = MutableStateFlow(MainScreenManagedState(pagerMode = true))

    val state = combine(useCase.diagrams, _managedState) { diagrams, managedState ->
        when {
            diagrams == null -> MainScreenState.Loading
            diagrams.isEmpty() -> MainScreenState.Empty
            else -> MainScreenState.Data(
                streaks = diagrams,
                pagerButtonEnabled = !managedState.pagerMode,
                gridButtonEnabled = managedState.pagerMode,
                onePageMode = managedState.pagerMode,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = MainScreenState.Loading,
    )

    init {
        viewModelScope.launch {
            setAlarms()
            useCase.init()
        }
    }

    fun toPagerMode() {
        _managedState.update { it.copy(pagerMode = true) }
    }

    fun toGridMode() {
        _managedState.update { it.copy(pagerMode = false) }
    }

    private suspend fun setAlarms() {
        val timeToFail = settingsRepository.getTimeToFail()
        val howOften = settingsRepository.getHowOften()
        val howManyTimes = settingsRepository.getHowManyTimes()

        alarmsManagement.setAlarms(
            timeToFail = timeToFail,
            howOftenMinutes = howOften,
            howManyTimes = howManyTimes,
        )
    }
}