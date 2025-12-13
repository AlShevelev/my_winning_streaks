package com.shevelev.mywinningstreaks.screens.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.GlassPanel
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.NewStreakBottomSheet
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.Stub
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.Arc
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.CircleDiagram
import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenState
import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenViewModel
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.background
import mywinningstreaks.composeapp.generated.resources.empty_list
import mywinningstreaks.composeapp.generated.resources.loading
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MainScreenRoot(
    viewModel: MainScreenViewModel = koinViewModel(),
) {
    var showNewStreakBottomSheet by remember { mutableStateOf(false) }

    if (showNewStreakBottomSheet) {
        NewStreakBottomSheet(
            onDismiss = { showNewStreakBottomSheet = false }
        )
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    val dimensions = LocalDimensions.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(Res.drawable.background),
                contentScale = ContentScale.FillHeight,
            )
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                when (val s = state.value) {
                    is MainScreenState.Data -> {
                        CircleDiagram(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .align(Alignment.Center)
                                .padding(all = 48.dp),
                            animated = true,
                            arcs = s.streak.arcs.map {
                                Arc(
                                    from = it.from,
                                    to = it.to,
                                    color = when (it.status) {
                                        Status.Marked -> Color.Green
                                        Status.Skipped -> Color.Red
                                        Status.Sick -> Color.Yellow
                                        Status.Unknown -> Color.Gray
                                    },
                                )
                            },
                        )
                    }

                    is MainScreenState.Empty -> Stub(
                        text = stringResource(Res.string.empty_list),
                        modifier = Modifier.align(Alignment.Center),
                    )

                    is MainScreenState.Loading -> Stub(
                        text = stringResource(Res.string.loading),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = dimensions.paddingTriple),
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                GlassPanel(
                    modifier = Modifier.padding(bottom = 50.dp),
                    addButtonEnabled = state.value.addButtonEnabled,
                    pagerButtonEnabled = state.value.pagerButtonEnabled,
                    gridButtonEnabled = state.value.gridButtonEnabled,
                    settingsButtonEnabled = state.value.settingsButtonEnabled,
                    onAddButtonClick = { showNewStreakBottomSheet = true }
                )
            }
        }
    }
}