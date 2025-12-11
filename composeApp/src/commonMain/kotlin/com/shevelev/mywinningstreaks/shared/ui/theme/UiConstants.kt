package com.shevelev.mywinningstreaks.shared.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

internal data class UiConstants(
    val animationStandardDuration: Long = 300,
)

internal val LocalUiConstants = staticCompositionLocalOf<UiConstants> {
    UiConstants()
}
