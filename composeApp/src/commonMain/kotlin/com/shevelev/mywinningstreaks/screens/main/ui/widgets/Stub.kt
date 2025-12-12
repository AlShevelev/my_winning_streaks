package com.shevelev.mywinningstreaks.screens.main.ui.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.shevelev.mywinningstreaks.shared.ui.OutlinedText

@Composable
internal fun Stub(
    text: String,
    modifier: Modifier = Modifier,
) {
    OutlinedText(
        text = text,
        outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
        fillColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge.copy(fontStyle = FontStyle.Italic),
        textAlign = TextAlign.Center,
    )
}