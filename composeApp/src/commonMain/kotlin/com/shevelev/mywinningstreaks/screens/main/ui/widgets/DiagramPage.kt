package com.shevelev.mywinningstreaks.screens.main.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.Arc
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.CircleDiagram
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.glasspanels.GlassPanelDiagramMenu
import com.shevelev.mywinningstreaks.shared.ui.OutlinedText
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import com.shevelev.mywinningstreaks.shared.ui.theme.color.additional
import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.fails
import mywinningstreaks.composeapp.generated.resources.last_days
import mywinningstreaks.composeapp.generated.resources.sicks
import mywinningstreaks.composeapp.generated.resources.wins
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiagramPage(
    streak: Streak,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        OutlinedText(
            text = streak.title,
            outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            fillColor = MaterialTheme.colorScheme.surfaceVariant,
            style = MaterialTheme.typography.headlineLarge
                .copy(fontStyle = FontStyle.Italic),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = dimensions.paddingSingle),
        )

        OutlinedText(
            text = stringResource(Res.string.last_days, streak.totalDaysToShow),
            outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            fillColor = MaterialTheme.colorScheme.surfaceVariant,
            style = MaterialTheme.typography.headlineSmall
                .copy(fontStyle = FontStyle.Italic),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = dimensions.paddingSingle),
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(top = dimensions.paddingDouble)
        ) {
            CircleDiagram(
                modifier = Modifier
                    .padding(horizontal = dimensions.diagramSidePadding)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                animated = true,
                lineWidth = dimensions.diagramLineWidth,
                arcs = streak.arcs.map {
                    Arc(
                        from = it.from,
                        to = it.to,
                        color = when (it.status) {
                            Status.Won -> MaterialTheme.colorScheme.additional.diagramWon
                            Status.Failed -> MaterialTheme.colorScheme.additional.diagramFailed
                            Status.Sick -> MaterialTheme.colorScheme.additional.diagramSick
                            Status.Unknown -> MaterialTheme.colorScheme.additional.diagramUnknown
                        },
                    )
                },
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensions.paddingSingle),
            ) {
                StatisticsLine(
                    text = stringResource(Res.string.wins, streak.winDays, streak.totalDays),
                    color = MaterialTheme.colorScheme.additional.diagramWon,
                )

                StatisticsLine(
                    text = stringResource(Res.string.sicks, streak.sickDays, streak.totalDays),
                    color = MaterialTheme.colorScheme.additional.diagramSick,
                )

                StatisticsLine(
                    text = stringResource(Res.string.fails, streak.failDays, streak.totalDays),
                    color = MaterialTheme.colorScheme.additional.diagramFailed,
                )
            }
        }

        GlassPanelDiagramMenu(
            modifier = Modifier
                .padding(top = dimensions.paddingSingle)
                .scale(0.75f)
                .align(Alignment.CenterHorizontally),
            markButtonEnabled = true,
        )
    }
}

@Composable
private fun StatisticsLine(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    OutlinedText(
        text = text,
        outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
        fillColor = color,
        style = MaterialTheme.typography.headlineSmall
            .copy(fontStyle = FontStyle.Italic),
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}