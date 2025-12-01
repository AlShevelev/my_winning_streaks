package com.shevelev.mywinningstreaks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.ic_add
import mywinningstreaks.composeapp.generated.resources.ic_circle
import mywinningstreaks.composeapp.generated.resources.ic_four_circles
import mywinningstreaks.composeapp.generated.resources.ic_settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    surfaceColor: Color = MaterialTheme.colorScheme.surface,
    shadowColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    val backgroundColor = remember { surfaceColor.copy(alpha = 0.5f) }
    val shadowColor = remember { shadowColor.copy(alpha = 0.25f) }

    val shape = remember { RoundedCornerShape(800.dp) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 5.dp,
                    offset = DpOffset(0.dp, 5.dp),
                    color = shadowColor,
                    blendMode = BlendMode.SrcOver,
                )
            )
            .clip(shape)
            .background(backgroundColor)
    ) {
        GlassPanelButton(
            icon = Res.drawable.ic_add,
            internalPadding = 0.dp,
        )

        Divider()

        GlassPanelButton(
            icon = Res.drawable.ic_circle,
            internalPadding = 6.dp,
        )

        GlassPanelButton(
            icon = Res.drawable.ic_four_circles,
            internalPadding = 6.dp,
            enabled = false,
        )

        Divider()

        GlassPanelButton(
            icon = Res.drawable.ic_settings,
            internalPadding = 2.dp,
        )
    }
}

@Composable
private fun GlassPanelButton(
    icon: DrawableResource,
    internalPadding: Dp,
    enabled: Boolean = true,
) {
    Icon(
        painter = painterResource(icon),
        contentDescription = null,
        tint = if (enabled) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.outline
        },
        modifier = Modifier
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                onClick = {}
            )
            .padding(8.dp)
            .size(40.dp)
            .padding(internalPadding)
    )
}

@Composable
private fun Divider() {
    VerticalDivider(
        color = MaterialTheme.colorScheme.onSurface,
        thickness = 2.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxHeight()
    )
}