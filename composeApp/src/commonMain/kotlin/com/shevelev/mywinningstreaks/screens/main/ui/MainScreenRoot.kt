package com.shevelev.mywinningstreaks.screens.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.DiagramGrid
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.DiagramPager
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.Stub
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.NewStreakBottomSheet
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.glasspanels.GlassPanelMainMenu
import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenState
import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenViewModel
import com.shevelev.mywinningstreaks.shared.navigation.Routes
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.CheckerPermissions
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
    navController: NavHostController,
    viewModel: MainScreenViewModel = koinViewModel(),
) {
    CheckerPermissions()

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
                        if (s.onePageMode) {
                            DiagramPager(
                                streaks = s.streaks,
                                modifier = Modifier
                                    .padding(horizontal = dimensions.paddingSingle)
                                    .fillMaxSize(),
                            )
                        } else {
                            DiagramGrid(
                                streaks = s.streaks,
                                modifier = Modifier
                                    .padding(top = dimensions.paddingDouble)
                                    .padding(horizontal = dimensions.paddingSingle)
                                    .fillMaxSize(),
                            )
                        }
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

            GlassPanelMainMenu(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 50.dp),
                addButtonEnabled = state.value.addButtonEnabled,
                pagerButtonEnabled = state.value.pagerButtonEnabled,
                gridButtonEnabled = state.value.gridButtonEnabled,
                settingsButtonEnabled = state.value.settingsButtonEnabled,
                onAddButtonClick = { showNewStreakBottomSheet = true },
                onPagerButtonClick = { viewModel.toPagerMode() },
                onGridButtonClick = { viewModel.toGridMode() },
                onSettingsButtonClick = { navController.navigate(Routes.Settings.name) }
            )
        }
    }
}
