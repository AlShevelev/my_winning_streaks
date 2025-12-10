package com.shevelev.mywinningstreaks.di

import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val AppModuleCommon = module {
    viewModel {
        MainScreenViewModel()
    }
}