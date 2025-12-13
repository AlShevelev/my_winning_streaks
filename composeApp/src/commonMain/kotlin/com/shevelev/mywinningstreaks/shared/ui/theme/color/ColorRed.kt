package com.shevelev.mywinningstreaks.shared.ui.theme.color

import androidx.compose.ui.graphics.Color

/**
 * From here: https://material-foundation.github.io/material-theme-builder/
 */
internal object ColorRed {
    object Light : ThemeColorSet {
        override val primary = Color(0xFF8f4c38)
        override val secondary = Color(0xFF77574e)
        override val tertiary = Color(0xFF6c5d2f)
        override val error = Color(0xFFba1a1a)

        override val onPrimary = Color(0xFFffffff)
        override val onSecondary = Color(0xFFffffff)
        override val onTertiary = Color(0xFFffffff)
        override val onError = Color(0xFFffffff)

        override val containerPrimary = Color(0xFFffdbd1)
        override val containerSecondary = Color(0xFFffdbd1)
        override val containerTertiary = Color(0xFFf5e1a7)
        override val containerError = Color(0xFFffdad6)

        override val onContainerPrimary = Color(0xFF723523)
        override val onContainerSecondary = Color(0xFF5d4037)
        override val onContainerTertiary = Color(0xFF534619)
        override val onContainerError = Color(0xFF93000a)

        override val surfaceDim = Color(0xFFe8d6d2)
        override val surface = Color(0xFFfff8f6)
        override val surfaceVariant = Color(0xFFf1dfd9)
        override val surfaceBright = Color(0xFFfff8f6)
        override val surfaceInverse = Color(0xFF392e2b)

        override val surfaceContainerLowest = Color(0xFFffffff)
        override val surfaceContainerLow = Color(0xFFfff1ed)
        override val surfaceContainer = Color(0xFFfceae5)
        override val surfaceContainerHigh = Color(0xFFf7e4e0)
        override val surfaceContainerHighest = Color(0xFFf1dfda)

        override val inverseOnSurface = Color(0xFFffede8)
        override val inversePrimary = Color(0xFFffb5a0)

        override val onSurface = Color(0xFF231917)
        override val onSurfaceVariant = Color(0xFF53433f)

        override val outline = Color(0xFF85736e)
        override val outlineVariant = Color(0xFFd8c2bc)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFFfff8f6)
        override val onBackground = Color(0xFF231917)
    }

    object Dark : ThemeColorSet {
        override val primary = Color(0xFFffb5a0)
        override val secondary = Color(0xFFe7bdb2)
        override val tertiary = Color(0xFFd8c58d)
        override val error = Color(0xFFffb4ab)

        override val onPrimary = Color(0xFF561f0f)
        override val onSecondary = Color(0xFF442a22)
        override val onTertiary = Color(0xFF3b2f05)
        override val onError = Color(0xFF690005)

        override val containerPrimary = Color(0xFF723523)
        override val containerSecondary = Color(0xFF5d4037)
        override val containerTertiary = Color(0xFF534619)
        override val containerError = Color(0xFF93000a)

        override val onContainerPrimary = Color(0xFFffdbd1)
        override val onContainerSecondary = Color(0xFFffdbd1)
        override val onContainerTertiary = Color(0xFFf5e1a7)
        override val onContainerError = Color(0xFFffdad6)

        override val surfaceDim = Color(0xFF1a110f)
        override val surface = Color(0xFF1a110f)
        override val surfaceVariant = Color(0xFF504340)
        override val surfaceBright = Color(0xFF423734)
        override val surfaceInverse = Color(0xFFf1dfda)

        override val surfaceContainerLowest = Color(0xFF140c0a)
        override val surfaceContainerLow = Color(0xFF231917)
        override val surfaceContainer = Color(0xFF271d1b)
        override val surfaceContainerHigh = Color(0xFF322825)
        override val surfaceContainerHighest = Color(0xFF3d322f)

        override val inverseOnSurface = Color(0xFF392e2b)
        override val inversePrimary = Color(0xFF8f4c38)

        override val onSurface = Color(0xFFf1dfda)
        override val onSurfaceVariant = Color(0xFFd8c2bc)

        override val outline = Color(0xFFa08c87)
        override val outlineVariant = Color(0xFF53433f)

        override val scrim = Color(0xFF000000)
        override val shadow = Color(0xFF000000)

        override val background = Color(0xFF1a110f)
        override val onBackground = Color(0xFFf1dfda)
    }
}
