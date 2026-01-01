package com.shevelev.mywinningstreaks.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MainScreenViewModel(
    private val useCase: DiagramUseCase,
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
            useCase.init()
        }
    }

    fun toPagerMode() {
        _managedState.update { it.copy(pagerMode = true) }
    }

    fun toGridMode() {
        _managedState.update { it.copy(pagerMode = false) }
    }
}