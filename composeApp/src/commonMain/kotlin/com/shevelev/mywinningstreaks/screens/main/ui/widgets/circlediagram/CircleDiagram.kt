package com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
internal fun CircleDiagram(
    modifier: Modifier = Modifier,
    arcs: List<Arc>,
    animated: Boolean = true,
    lineWidth: Dp,
    animationDurationMillis: Int = 500,
) {
    val animateFloat = remember {
        Animatable(if (animated) 0f else 1f)
    }

    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = if (animated) animationDurationMillis else 0,
                easing = LinearEasing,
            ))
    }

    val strokeWidth = with(LocalDensity.current) { lineWidth.toPx() }

    Box(
        modifier = modifier
            .drawWithCache {
                val radius = size.width / 2
                val topLeft = Offset(size.width / 2 - radius, size.height / 2 - radius)

                onDrawBehind {
                    for (arc in arcs) {
                        val toValue = when {
                            arc.from >= animateFloat.value -> continue
                            arc.to <= animateFloat.value -> arc.to
                            else -> animateFloat.value
                        }

                        drawArc(
                            color = arc.color,
                            startAngle = 360f*arc.from - 90,
                            sweepAngle = 360f*(toValue - arc.from),
                            useCenter = false,
                            topLeft = topLeft,
                            size = size,
                            style = Stroke(strokeWidth)
                        )
                    }
                }
            }
    )
}
