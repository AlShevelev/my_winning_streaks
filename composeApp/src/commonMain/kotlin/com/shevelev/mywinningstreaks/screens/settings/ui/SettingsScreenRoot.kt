package com.shevelev.mywinningstreaks.screens.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.SettingsTopBar
import com.shevelev.mywinningstreaks.screens.settings.viewmodel.SettingsScreenViewModel
import com.shevelev.mywinningstreaks.shared.ui.ListItemPicker
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.days_10
import mywinningstreaks.composeapp.generated.resources.days_14
import mywinningstreaks.composeapp.generated.resources.days_180
import mywinningstreaks.composeapp.generated.resources.days_30
import mywinningstreaks.composeapp.generated.resources.days_365
import mywinningstreaks.composeapp.generated.resources.days_60
import mywinningstreaks.composeapp.generated.resources.days_7
import mywinningstreaks.composeapp.generated.resources.days_90
import mywinningstreaks.composeapp.generated.resources.recent_days_to_show
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingsScreenRoot(
    navController: NavHostController,
    viewModel: SettingsScreenViewModel = koinViewModel(),
) {
    Scaffold(
        topBar = {
            SettingsTopBar(
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val state = viewModel.state.collectAsStateWithLifecycle()

        val dimensions = LocalDimensions.current

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensions.paddingDouble)
        ) {
            Text(
                text = stringResource(Res.string.recent_days_to_show),
                style = MaterialTheme.typography.titleLarge,
            )

            RecentDaysSelection(
                possibleRecentDaysToShow = state.value.possibleRecentDaysToShow,
                selectedRecentDaysToShow = state.value.selectedRecentDaysToShow,
                onValueChange = { viewModel.setSelectedRecentDaysToShow(it) }
            )
        }
    }
}

@Composable
private fun RecentDaysSelection(
    possibleRecentDaysToShow: List<Int>,
    selectedRecentDaysToShow: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    ListItemPicker(
        list = possibleRecentDaysToShow,
        value = selectedRecentDaysToShow,
        modifier = modifier,
        label = {
            when (it) {
                7 -> stringResource(Res.string.days_7)
                10 -> stringResource(Res.string.days_10)
                14 -> stringResource(Res.string.days_14)
                30 -> stringResource(Res.string.days_30)
                60 -> stringResource(Res.string.days_60)
                90 -> stringResource(Res.string.days_90)
                180 -> stringResource(Res.string.days_180)
                365 -> stringResource(Res.string.days_365)
                else -> ""

            }
        },
        onValueChange = onValueChange
    )
}