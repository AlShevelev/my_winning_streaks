package com.shevelev.mywinningstreaks.di

import com.shevelev.mywinningstreaks.shared.alarms.AlarmsManagement
import com.shevelev.mywinningstreaks.shared.alarms.AlarmsManagementImpl
import org.koin.dsl.module

val AppModuleAndroid = module {
    factory<AlarmsManagement> {
        AlarmsManagementImpl(
            context = get(),
        )
    }
}