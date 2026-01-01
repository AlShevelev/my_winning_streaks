package com.shevelev.mywinningstreaks.shared.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class Dimensions(
    val paddingOneUnit: Dp = 1.dp,
    val paddingQuarter: Dp = 2.dp,
    val paddingThird: Dp = 3.dp,
    val paddingHalf: Dp = 4.dp,
    val paddingSingle: Dp = 8.dp,
    val paddingSingleAndHalf: Dp = 12.dp,
    val paddingDouble: Dp = 16.dp,
    val paddingDoubleAndHalf: Dp = 20.dp,
    val paddingTriple: Dp = 24.dp,

    val dialogCorners: Dp = 28.dp,
    val dialogContent: Dp = 24.dp,

    val outlineTextDefaultStroke: Dp = 4.dp,

    val diagramSidePadding: Dp = 40.dp,
    val diagramSidePaddingSmall: Dp = 20.dp,
    val diagramLineWidth: Dp = 20.dp,
    val diagramLineWidthThin: Dp = 10.dp,
    val diagramPagerIndicatorSize: Dp = 12.dp,
    val diagramPagerIndicatorStroke: Dp = 2.dp,
    val diagramStatisticsStroke: Dp = 3.dp,
)

internal val LocalDimensions = staticCompositionLocalOf<Dimensions> {
    Dimensions()
}
