package com.shevelev.mywinningstreaks.shared.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun GeneralTextButton(
    onClick: suspend () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: DrawableResource? = null,
    text: String,
) {
    val scope = rememberCoroutineScope()
    val dimensions = LocalDimensions.current

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            scope.launch {
                delay(300)
                onClick()
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = dimensions.paddingSingle)
                )
            }

            Text(text = text)
        }
    }
}