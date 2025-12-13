package com.shevelev.mywinningstreaks.shared.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

internal object AdditionalColors {
    val diagramMarked = Color(0xFF00FF00)
    val diagramSkipped = Color(0xFFFF0000)
    val diagramSick = Color(0xFFFFFF00)
    val diagramUnknown = Color(0xFFA7A7A7)
}

internal val ColorScheme.additional: AdditionalColors
    get() = AdditionalColors