package com.shevelev.mywinningstreaks.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions

@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    fillColor: Color = Color.Unspecified,
    outlineColor: Color,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    style: TextStyle = LocalTextStyle.current,
    strokeWidth: Dp = LocalDimensions.current.outlineTextDefaultStroke,
) {
    val strokeWidth = with(LocalDensity.current) { strokeWidth.toPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier.semantics { hideFromAccessibility() },
            color = outlineColor,
            textDecoration = null,
            textAlign = textAlign,
            overflow = overflow,
            softWrap = softWrap,
            style = style.copy(
                drawStyle = Stroke(width = strokeWidth)
            ),
        )

        Text(
            text = text,
            color = fillColor,
            textDecoration = textDecoration,
            textAlign = textAlign,
            overflow = overflow,
            softWrap = softWrap,
            style = style,
        )
    }
}