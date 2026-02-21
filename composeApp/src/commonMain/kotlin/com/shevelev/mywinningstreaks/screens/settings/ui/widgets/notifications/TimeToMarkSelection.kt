package com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.TextSlider
import kotlinx.datetime.LocalTime

@Composable
internal fun TimeToMarkSelection(
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
