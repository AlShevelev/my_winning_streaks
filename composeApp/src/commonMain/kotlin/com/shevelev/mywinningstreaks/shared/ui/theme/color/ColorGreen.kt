package com.shevelev.mywinningstreaks.shared.ui.theme.color

import androidx.compose.ui.graphics.Color

/**
 * From here: https://material-foundation.github.io/material-theme-builder/
 */
internal object ColorGreen {
    object Light : ThemeColorSet {
        override val primary = Color(0xFF4c662b)
        override val secondary = Color(0xFF586249)
        override val tertiary = Color(0xFF386663)
        override val error = Color(0xFFba1a1a)

        override val onPrimary = Color(0xFFffffff)
        override val onSecondary = Color(0xFFffffff)
        override val onTertiary = Color(0xFFffffff)
        override val onError = Color(0xFFffffff)

        override val containerPrimary = Color(0xFFcdeda3)
        override val containerSecondary = Color(0xFFdce7c8)
        override val containerTertiary = Color(0xFFbcece7)
        override val containerError = Color(0xFFffdad6)

        override val onContainerPrimary = Color(0xFF354e16)
        override val onContainerSecondary = Color(0xFF404a33)
        override val onContainerTertiary = Color(0xFF1f4e4b)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFFdadbd0)
        override val surface = Color(0xFFf9faef)
        override val surfaceVariant = Color(0xFFe1e4d7)
        override val surfaceBright = Color(0xFFf9faef)
        override val surfaceInverse = Color(0xFF2f312a)

        override val surfaceContainerLowest = Color(0xFFffffff)
        override val surfaceContainerLow = Color(0xFFf3f4e9)
        override val surfaceContainer = Color(0xFFeeefe3)
        override val surfaceContainerHigh = Color(0xFFe8e9de)
        override val surfaceContainerHighest = Color(0xFFe2e3d8)

        override val inverseOnSurface = Color(0xFFf1f2e6)
        override val inversePrimary = Color(0xFFb1d18a)

        override val onSurface = Color(0xFF1a1c16)
        override val onSurfaceVariant = Color(0xFF44483d)

        override val outline = Color(0xFF75796c)
        override val outlineVariant = Color(0xFFc5c8ba)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFFf9faef)
        override val onBackground = Color(0xFF1a1c16)
    }

    object Dark : ThemeColorSet {
        override val primary = Color(0xFFb1d18a)
        override val secondary = Color(0xFFbfcbad)
        override val tertiary = Color(0xFFa0d0cb)
        override val error = Color(0xFFffb4ab)

        override val onPrimary = Color(0xFF1f3701)
        override val onSecondary = Color(0xFF2a331e)
        override val onTertiary = Color(0xFF003735)
        override val onError = Color(0xFF690005)

        override val containerPrimary = Color(0xFF354e16)
        override val containerSecondary = Color(0xFF404a33)
        override val containerTertiary = Color(0xFF1f4e4b)
        override val containerError = Color(0xFF93000a)

        override val onContainerPrimary = Color(0xFFcdeda3)
        override val onContainerSecondary = Color(0xFFdce7c8)
        override val onContainerTertiary = Color(0xFFbcece7)
        override val onContainerError = Color(0xFFffdad6)

        override val surfaceDim = Color(0xFF12140e)
        override val surface = Color(0xFF12140e)
        override val surfaceVariant = Color(0xFF44483e)
        override val surfaceBright = Color(0xFF383a32)
        override val surfaceInverse = Color(0xFFe2e3d8)

        override val surfaceContainerLowest = Color(0xFF0c0f09)
        override val surfaceContainerLow = Color(0xFF1a1c16)
        override val surfaceContainer = Color(0xFF1e201a)
        override val surfaceContainerHigh = Color(0xFF282b24)
        override val surfaceContainerHighest = Color(0xFF33362e)

        override val inverseOnSurface = Color(0xFF2f312a)
        override val inversePrimary = Color(0xFF4c662b)

        override val onSurface = Color(0xFFe2e3d8)
        override val onSurfaceVariant = Color(0xFFc5c8ba)

        override val outline = Color(0xFF8f9285)
        override val outlineVariant = Color(0xFF44483d)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFF12140e)
        override val onBackground = Color(0xFFe2e3d8)
    }
}
