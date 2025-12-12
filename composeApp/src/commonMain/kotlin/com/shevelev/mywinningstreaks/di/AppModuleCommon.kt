package com.shevelev.mywinningstreaks.di

import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenViewModel
import com.shevelev.mywinningstreaks.shared.usecases.DiagramArcCalculator
import com.shevelev.mywinningstreaks.shared.usecases.DiagramArcCalculatorImpl
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val AppModuleCommon = module {
    viewModel {
        MainScreenViewModel(
            useCase = get(),
        )
    }

    factory<DiagramArcCalculator> {
        DiagramArcCalculatorImpl()
    }

    single<DiagramUseCase> {
        DiagramUseCaseImpl(
            settingsRepository = get(),
            databaseRepository = get(),
            diagramArcCalculator = get(),
        )
    }
}