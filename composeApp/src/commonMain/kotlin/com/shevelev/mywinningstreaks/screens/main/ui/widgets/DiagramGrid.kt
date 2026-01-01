package com.shevelev.mywinningstreaks.screens.main.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.Arc
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.CircleDiagram
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.EditStreakTitleBottomSheet
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.MarkBottomSheet
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.StreakDeleteConfirmationDialog
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.glasspanels.GlassPanelDiagramMenu
import com.shevelev.mywinningstreaks.shared.ui.OutlinedText
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import com.shevelev.mywinningstreaks.shared.ui.theme.color.additional
import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.fails_brief
import mywinningstreaks.composeapp.generated.resources.sicks_brief
import mywinningstreaks.composeapp.generated.resources.wins_brief
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiagramGrid(
    streaks: List<Streak>,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyGridState()

    val dimensions = LocalDimensions.current

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        modifier = modifier
            .graphicsLayer { alpha = 0.99F }
            .drawWithContent {
                val colorStops = listOf(
                    Pair(0f, Color.Transparent),
                    Pair(0.05f, Color.Black),
                    Pair(0.95f, Color.Black),
                    Pair(1f, Color.Transparent),
                )
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        *colorStops.toTypedArray(),
                        endY = size.height,
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }
    ) {
        items(
            count = streaks.size,
            key = { index -> streaks[index].id },
            itemContent = { index ->
                DiagramGridItem(
                    streak = streaks[index],
                    modifier = Modifier.padding(top = dimensions.paddingTriple)
                )
            }
        )
    }
}

@Composable
internal fun DiagramGridItem(
    streak: Streak,
    modifier: Modifier = Modifier,
) {
    var showEditTitleBottomSheet by remember { mutableStateOf(false) }
    if (showEditTitleBottomSheet) {
        EditStreakTitleBottomSheet(
            id = streak.id,
            title = streak.title,
            onDismiss = { showEditTitleBottomSheet = false }
        )
    }

    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    if (showConfirmDeleteDialog) {
        StreakDeleteConfirmationDialog(
            id = streak.id,
            onDismiss = { showConfirmDeleteDialog = false },
        )
    }

    var showMarkDialog by remember { mutableStateOf(false) }
    if (showMarkDialog) {
        MarkBottomSheet(
            id = streak.id,
            onDismiss = { showMarkDialog = false },
        )
    }

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
            style = MaterialTheme.typography.bodyLarge
                .copy(fontStyle = FontStyle.Italic),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(vertical = dimensions.paddingSingle)
        ) {
            CircleDiagram(
                modifier = Modifier
                    .padding(horizontal = dimensions.diagramSidePaddingSmall)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                animated = false,
                lineWidth = dimensions.diagramLineWidthThin,
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
                val winsPercent = getPercent(streak.winDays, streak.totalDays)
                StatisticsLine(
                    text = stringResource(Res.string.wins_brief, winsPercent),
                    color = MaterialTheme.colorScheme.additional.diagramWon,
                    outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                val sicksPercent = getPercent(streak.sickDays, streak.totalDays)
                StatisticsLine(
                    text = stringResource(Res.string.sicks_brief, sicksPercent),
                    color = MaterialTheme.colorScheme.additional.diagramSick,
                    outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                StatisticsLine(
                    text = stringResource(Res.string.fails_brief, 100 - winsPercent - sicksPercent),
                    color = MaterialTheme.colorScheme.additional.diagramFailed,
                    outlineColor = MaterialTheme.colorScheme.surface,
                )
            }
        }

        GlassPanelDiagramMenu(
            modifier = Modifier
                .scale(0.75f)
                .align(Alignment.CenterHorizontally),
            markButtonEnabled = streak.canMark,
            onMarkButtonClick = { showMarkDialog = true },
            onEditButtonClick = { showEditTitleBottomSheet = true },
            onDeleteButtonClick = { showConfirmDeleteDialog = true }
        )
    }
}

@Composable
private fun StatisticsLine(
    text: String,
    color: Color,
    outlineColor: Color,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    OutlinedText(
        text = text,
        outlineColor = outlineColor,
        fillColor = color,
        style = MaterialTheme.typography.bodyLarge
            .copy(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        modifier = modifier,
        strokeWidth = dimensions.diagramStatisticsStroke,
    )
}
