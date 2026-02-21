package com.shevelev.mywinningstreaks.screens.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.SettingsTopBar
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications.NotificationsSettingsSection
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications.PermissionsButton
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications.RecentDaysSelection
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications.TimeToMarkSelection
import com.shevelev.mywinningstreaks.screens.settings.viewmodel.SettingsScreenState
import com.shevelev.mywinningstreaks.screens.settings.viewmodel.SettingsScreenViewModel
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.notifications_title
import mywinningstreaks.composeapp.generated.resources.recent_days_to_show
import mywinningstreaks.composeapp.generated.resources.time_to_mark_as_failed
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

        when (val stateValue = state.value) {
            is SettingsScreenState.Loading -> {}

            is SettingsScreenState.Data -> {
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
                        recentDaysToShowValues = stateValue.recentDaysToShowValues,
                        recentDaysToShow = stateValue.recentDaysToShow,
                        modifier = Modifier
                            .padding(horizontal = dimensions.paddingDouble)
                            .fillMaxWidth(),
                        onValueChange = { viewModel.setRecentDaysToShow(it) }
                    )

                    Text(
                        text = stringResource(Res.string.time_to_mark_as_failed),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = dimensions.paddingTriple),
                    )

                    TimeToMarkSelection(
                        timeToStart = stateValue.timeToStart,
                        onValueChange = { viewModel.setTimeToFail(it) },
                        modifier = Modifier
                            .padding(horizontal = dimensions.paddingDouble)
                            .fillMaxWidth(),
                    )

                    Text(
                        text = stringResource(Res.string.notifications_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = dimensions.paddingTriple),
                    )

                    if (stateValue.askPermissionsButtonVisible) {
                        PermissionsButton(
                            modifier = Modifier
                                .padding(top = dimensions.paddingDouble)
                                .fillMaxWidth(),
                            permissionGranted = viewModel::permissionGranted,
                        )
                    } else {
                        NotificationsSettingsSection(
                            stateValue = stateValue,
                            setHowOften = viewModel::setHowOften,
                            setHowManyTimes = viewModel::setHowManyTimes,
                        )
                    }
                }
            }
        }
    }
}
