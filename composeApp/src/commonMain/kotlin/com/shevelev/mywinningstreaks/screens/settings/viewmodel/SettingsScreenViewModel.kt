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

    var startTimeToFail: LocalTime? = null
    var endTimeToFail: LocalTime? = null

    var startHowOften: Int? = null
    var endHowOften: Int? = null

    var startHowManyTimes: Int? = null
    var endHowManyTimes: Int? = null

    init {
        viewModelScope.launch {
            startTimeToFail = settingsRepository.getTimeToFail()
            startHowOften = settingsRepository.getHowOften()
            startHowManyTimes = settingsRepository.getHowManyTimes()

            val newState = SettingsScreenState.Data(
                recentDaysToShowValues = listOf(7, 10, 14, 30, 60, 90, 180, 365),
                recentDaysToShow = settingsRepository.getDaysToShow(),
                timeToStart = requireNotNull(startTimeToFail),
                howOften = requireNotNull(startHowOften),
                howOftenValues = listOf(5, 10, 15),
                howManyTimes = requireNotNull(startHowManyTimes),
                howManyTimesValues = listOf(1, 2, 3),
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

    fun setTimeToFail(value: LocalTime) {
        viewModelScope.launch {
            settingsRepository.setTimeToFail(value)
            endTimeToFail = value

            (_state.value as? SettingsScreenState.Data)
                ?.copy(timeToStart = value)
                ?.let { _state.emit(it) }
        }
    }

    fun setHowOften(value: Int) {
        viewModelScope.launch {
            settingsRepository.setHowOften(value)
            endHowOften = value

            (_state.value as? SettingsScreenState.Data)
                ?.copy(howOften = value)
                ?.let { _state.emit(it) }
        }
    }

    fun setHowManyTimes(value: Int) {
        viewModelScope.launch {
            settingsRepository.setHowManyTimes(value)
            endHowManyTimes = value

            (_state.value as? SettingsScreenState.Data)
                ?.copy(howManyTimes = value)
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
            val timeToFail = endTimeToFail ?: startTimeToFail
            val howOften = endHowOften ?: startHowOften
            val howManyTimes = endHowManyTimes ?: startHowManyTimes

            if (timeToFail != startTimeToFail || howOften != startHowOften || howManyTimes != startHowManyTimes) {
                alarmsManagement.setAlarms(
                    timeToFail = requireNotNull(timeToFail),
                    howOftenMinutes = requireNotNull(howOften),
                    howManyTimes = requireNotNull(howManyTimes)
                )
            }
        }.onFailure {
            println(it.toString())
        }
    }
}