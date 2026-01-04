package com.shevelev.mywinningstreaks.screens.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.SettingsTopBar
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.TextSlider
import com.shevelev.mywinningstreaks.screens.settings.viewmodel.SettingsScreenState
import com.shevelev.mywinningstreaks.screens.settings.viewmodel.SettingsScreenViewModel
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import kotlinx.datetime.LocalTime
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.days_10
import mywinningstreaks.composeapp.generated.resources.days_14
import mywinningstreaks.composeapp.generated.resources.days_180
import mywinningstreaks.composeapp.generated.resources.days_30
import mywinningstreaks.composeapp.generated.resources.days_365
import mywinningstreaks.composeapp.generated.resources.days_60
import mywinningstreaks.composeapp.generated.resources.days_7
import mywinningstreaks.composeapp.generated.resources.days_90
import mywinningstreaks.composeapp.generated.resources.every_10_minutes
import mywinningstreaks.composeapp.generated.resources.every_15_minutes
import mywinningstreaks.composeapp.generated.resources.every_5_minutes
import mywinningstreaks.composeapp.generated.resources.how_many_times
import mywinningstreaks.composeapp.generated.resources.how_often
import mywinningstreaks.composeapp.generated.resources.notifications
import mywinningstreaks.composeapp.generated.resources.once
import mywinningstreaks.composeapp.generated.resources.recent_days_to_show
import mywinningstreaks.composeapp.generated.resources.three_times
import mywinningstreaks.composeapp.generated.resources.time_to_start
import mywinningstreaks.composeapp.generated.resources.twice
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
                        text = stringResource(Res.string.notifications),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = dimensions.paddingTriple),
                    )

                    Text(
                        text = stringResource(Res.string.time_to_start),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = dimensions.paddingDouble),
                    )

                    TimeToStartSelection(
                        timeToStart = stateValue.timeToStart,
                        onValueChange = { viewModel.setTimeToStart(it) },
                        modifier = Modifier
                            .padding(horizontal = dimensions.paddingDouble)
                            .fillMaxWidth(),
                    )

                    Text(
                        text = stringResource(Res.string.how_often),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = dimensions.paddingDouble),
                    )

                    HowOftenSelection(
                        howOftenValues = stateValue.howOftenValues,
                        howOften = stateValue.howOften,
                        onValueChange = { viewModel.setHowOften(it) },
                        modifier = Modifier
                            .padding(horizontal = dimensions.paddingDouble)
                            .fillMaxWidth(),
                        )

                    Text(
                        text = stringResource(Res.string.how_many_times),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = dimensions.paddingDouble),
                    )

                    HowManyTimesSelection(
                        howManyTimes = stateValue.howManyTimes,
                        howManyTimesValues = stateValue.howManyTimesValues,
                        onValueChange = { viewModel.setHowManyTimes(it) },
                        modifier = Modifier
                            .padding(horizontal = dimensions.paddingDouble)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentDaysSelection(
    recentDaysToShowValues: List<Int>,
    recentDaysToShow: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    TextSlider(
        values = recentDaysToShowValues,
        value = recentDaysToShow,
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
        onValueChange = {
            println(it)
            onValueChange(it)
        }
    )
}

@Composable
private fun HowOftenSelection(
    howOftenValues: List<Int>,
    howOften: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    TextSlider(
        values = howOftenValues,
        value = howOften,
        modifier = modifier,
        label = {
            when (it) {
                5 -> stringResource(Res.string.every_5_minutes)
                10 -> stringResource(Res.string.every_10_minutes)
                15 -> stringResource(Res.string.every_15_minutes)
                else -> ""
            }
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun HowManyTimesSelection(
    howManyTimesValues: List<Int>,
    howManyTimes: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    TextSlider(
        values = howManyTimesValues,
        value = howManyTimes,
        modifier = modifier,
        label = {
            when (it) {
                1 -> stringResource(Res.string.once)
                2 -> stringResource(Res.string.twice)
                3 -> stringResource(Res.string.three_times)
                else -> ""
            }
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun TimeToStartSelection(
    timeToStart: LocalTime,
    modifier: Modifier = Modifier,
    onValueChange: (LocalTime) -> Unit,
) {
    var timeToShow by remember { mutableStateOf(LocalTime(timeToStart.hour, timeToStart.minute)) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextSlider(
            values = (0..23).toList(),
            value = timeToStart.hour,
            label = {
                timeToShow = LocalTime(it, timeToShow.minute)
                ""
            },
            showLabel = false,
            onValueChange = { onValueChange(LocalTime(it, timeToStart.minute)) },
        )

        val hourToShow = if (timeToShow.hour == 0) "00" else timeToShow.hour.toString()
        val minuteToShow = if (timeToShow.minute == 0) "00" else timeToShow.minute.toString()
        Text(text = "$hourToShow : $minuteToShow")

        TextSlider(
            values = listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55),
            value = (timeToStart.minute / 5) * 5,
            showLabel = false,
            label = {
                timeToShow = LocalTime(timeToShow.hour, it)
                ""
            },
            onValueChange =  { onValueChange(LocalTime(timeToStart.hour, it)) },
        )
    }
}
