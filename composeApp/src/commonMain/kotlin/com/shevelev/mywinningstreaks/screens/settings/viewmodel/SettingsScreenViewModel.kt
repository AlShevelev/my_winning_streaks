package com.shevelev.mywinningstreaks.screens.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.mywinningstreaks.shared.alarms.AlarmsManagement
import com.shevelev.mywinningstreaks.shared.permissions.PermissionBridge
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
    private val permissionBridge: PermissionBridge,
    private val alarmsManagement: AlarmsManagement,
) : ViewModel() {
    private var updateJob: Job? = null

    private val _state = MutableStateFlow<SettingsScreenState>(SettingsScreenState.Loading)
    val state = _state.asStateFlow()

    var startTimeToNotify: LocalTime? = null
    var endTimeToNotify: LocalTime? = null

    init {
        viewModelScope.launch {
            startTimeToNotify = settingsRepository.getTimeToNotify()

            val newState = SettingsScreenState.Data(
                recentDaysToShowValues = listOf(7, 10, 14, 30, 60, 90, 180, 365),
                recentDaysToShow = settingsRepository.getDaysToShow(),
                timeToNotify = requireNotNull(startTimeToNotify),
                askPermissionsButtonVisible = !permissionBridge.isPermissionGranted()
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

    fun setTimeToNotify(value: LocalTime) {
        viewModelScope.launch {
            settingsRepository.setTimeToNotify(value)
            endTimeToNotify = value

            (_state.value as? SettingsScreenState.Data)
                ?.copy(timeToNotify = value)
                ?.let { _state.emit(it) }
        }
    }

    fun permissionGranted() {
        viewModelScope.launch {
            (_state.value as? SettingsScreenState.Data)
                ?.copy(askPermissionsButtonVisible = false)
                ?.let { _state.emit(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()

        runCatching {
            val timeToNotify = endTimeToNotify ?: startTimeToNotify

            if (timeToNotify != startTimeToNotify) {
                alarmsManagement.setAlarm(
                    timeToNotify = requireNotNull(timeToNotify),
                )
            }
        }.onFailure {
            println(it.toString())
        }
    }
}