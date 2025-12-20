package com.shevelev.mywinningstreaks.screens.main.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.Arc
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.CircleDiagram
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.EditStreakTitleBottomSheet
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.StreakDeleteConfirmationDialog
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.glasspanels.GlassPanelDiagramMenu
import com.shevelev.mywinningstreaks.shared.ui.OutlinedText
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import com.shevelev.mywinningstreaks.shared.ui.theme.color.additional
import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import kotlin.math.min
import kotlin.math.round
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.fails
import mywinningstreaks.composeapp.generated.resources.last_days
import mywinningstreaks.composeapp.generated.resources.sicks
import mywinningstreaks.composeapp.generated.resources.wins
import org.jetbrains.compose.resources.stringResource

private const val MAX_PAGES_INDICATOR = 15

@Composable
internal fun BoxScope.DiagramPager(
    streaks: List<Streak>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = {streaks.size })

    // To show animation first time only
    var animated by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        animated = false
    }

    HorizontalPager(state = pagerState) { pageIndex ->
        DiagramPage(
            streak = streaks[pageIndex],
            modifier = modifier,
            animated = animated,
        )
    }

    PagesIndicator(
        pagerState = pagerState
    )
}

@Composable
internal fun DiagramPage(
    streak: Streak,
    modifier: Modifier = Modifier,
    animated: Boolean,
) {
    val dimensions = LocalDimensions.current

    var showEditTitleBottomSheet by remember { mutableStateOf(false) }

    var showConfirmDeleteDialog by remember { mutableStateOf(false) }

    if (showEditTitleBottomSheet) {
        EditStreakTitleBottomSheet(
            id = streak.id,
            title = streak.title,
            onDismiss = { showEditTitleBottomSheet = false }
        )
    }

    if (showConfirmDeleteDialog) {
        StreakDeleteConfirmationDialog(
            id = streak.id,
            onDismiss = { showConfirmDeleteDialog = false },
        )
    }

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
                animated = animated,
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
                .padding(top = dimensions.paddingDouble)
                .scale(0.75f)
                .align(Alignment.CenterHorizontally),
            markButtonEnabled = true,
            onEditButtonClick = { showEditTitleBottomSheet = true },
            onDeleteButtonClick = { showConfirmDeleteDialog = true }
        )
    }
}

@Composable
private fun StatisticsLine(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    OutlinedText(
        text = text,
        outlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
        fillColor = color,
        style = MaterialTheme.typography.headlineMedium
            .copy(fontStyle = FontStyle.Italic),
        textAlign = TextAlign.Center,
        modifier = modifier,
        strokeWidth = dimensions.diagramStatisticsStroke,
    )
}

@Composable
private fun BoxScope.PagesIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    val dimensions = LocalDimensions.current

    val pagesTotal = min(pagerState.pageCount, MAX_PAGES_INDICATOR)
    val selectedIndex = round(
        pagerState.currentPage * (pagesTotal / pagerState.pageCount.toFloat())
    )
        .toInt()
        .let {
            if (it < 0) 0 else if (it > pagesTotal - 1) pagesTotal - 1 else it
        }

    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = dimensions.paddingTriple),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagesTotal) { iteration ->
            val color = if (selectedIndex == iteration) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
            Box(
                modifier = Modifier
                    .padding(dimensions.paddingQuarter)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = dimensions.diagramPagerIndicatorStroke,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = CircleShape,
                    )
                    .size(dimensions.diagramPagerIndicatorSize)
            )
        }
    }
}