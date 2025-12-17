package com.shevelev.mywinningstreaks.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class MainScreenViewModel(
    private val useCase: DiagramUseCase,
) : ViewModel() {
    private val _managedState = MutableStateFlow(MainScreenManagedState())

    val state = combine(useCase.diagrams, _managedState) { diagrams, managedState ->
        when {
            diagrams == null -> MainScreenState.Loading
            diagrams.isEmpty() -> MainScreenState.Empty
            else -> MainScreenState.Data(
                streaks = diagrams
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
}