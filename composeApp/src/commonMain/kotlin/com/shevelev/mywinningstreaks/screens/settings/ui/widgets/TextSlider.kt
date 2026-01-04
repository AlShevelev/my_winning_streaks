package com.shevelev.mywinningstreaks.screens.settings.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TextSlider(
    modifier: Modifier = Modifier,
    values: List<Int>,
    value: Int,
    onValueChange: (Int) -> Unit,
    showLabel: Boolean = true,
    label: (@Composable (Int) -> String) = { "" },
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var sliderPosition by remember { mutableFloatStateOf(values.indexOf(value).toFloat()) }

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = round(it) },
            onValueChangeFinished = {
                val value = values[round(sliderPosition).toInt()]
                onValueChange(value)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = values.size-2,
            valueRange = 0f..values.lastIndex.toFloat(),
            thumb = {
                TextSliderThumb()
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    thumbTrackGapSize = 0.dp,
                )
            }
        )

        val textValue = label(values[round(sliderPosition).toInt()])
        if (showLabel) {
            Text(text = textValue)
        }
    }
}

@Composable
private fun TextSliderThumb() {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}