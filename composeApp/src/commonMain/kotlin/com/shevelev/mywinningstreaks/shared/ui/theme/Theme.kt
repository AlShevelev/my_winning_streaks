package com.shevelev.mywinningstreaks.shared.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Suppress("DEPRECATION")
internal fun createColorScheme(colorSet: ThemeColorSet): ColorScheme =
    ColorScheme(
        primary = colorSet.primary,
        onPrimary = colorSet.onPrimary,
        primaryContainer = colorSet.containerPrimary,
        onPrimaryContainer = colorSet.onContainerPrimary,
        inversePrimary = colorSet.inversePrimary,
        secondary = colorSet.secondary,
        onSecondary = colorSet.onSecondary,
        secondaryContainer = colorSet.containerSecondary,
        onSecondaryContainer = colorSet.onContainerSecondary,
        tertiary = colorSet.tertiary,
        onTertiary = colorSet.onTertiary,
        tertiaryContainer = colorSet.containerTertiary,
        onTertiaryContainer = colorSet.onContainerTertiary,
        background = colorSet.background,
        onBackground = colorSet.onBackground,
        surface = colorSet.surface,
        onSurface = colorSet.onSurface,
        surfaceVariant = colorSet.surfaceVariant,
        onSurfaceVariant = colorSet.onSurfaceVariant,
        surfaceTint = colorSet.primary,
        inverseSurface = colorSet.surfaceInverse,
        inverseOnSurface = colorSet.inverseOnSurface,
        error = colorSet.error,
        onError = colorSet.onError,
        errorContainer = colorSet.containerError,
        onErrorContainer = colorSet.onContainerError,
        outline = colorSet.outline,
        outlineVariant = colorSet.outlineVariant,
        scrim = colorSet.scrim,
        surfaceBright = colorSet.surfaceBright,
        surfaceContainer = colorSet.surfaceContainer,
        surfaceContainerHigh = colorSet.surfaceContainerHigh,
        surfaceContainerHighest = colorSet.surfaceContainerHighest,
        surfaceContainerLow = colorSet.surfaceContainerLow,
        surfaceContainerLowest = colorSet.surfaceContainerLowest,
        surfaceDim = colorSet.surfaceDim,
    )

@Composable
internal fun MyWinningStreaksTheme(
    content: @Composable () -> Unit
) {
    // Light blue theme is the default one
    val colorScheme = createColorScheme(ColorBlue.Light)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}