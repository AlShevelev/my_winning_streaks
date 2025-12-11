package com.shevelev.mywinningstreaks.screens.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.GlassPanel
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.Arc
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.circlediagram.CircleDiagram
import com.shevelev.mywinningstreaks.screens.main.viewmodel.MainScreenViewModel
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MainScreenRoot(
    viewModel: MainScreenViewModel = koinViewModel(),
) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CircleDiagram(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                        .padding(all = 48.dp),
                    animated = true,
                    arcs = listOf(
                        Arc(
                            from = 0f,
                            to = 1f / 3f,
                            color = Color.Red,
                        ),
                        Arc(
                            from = 1f/3f,
                            to = 2f/3f,

                            color = Color.Green,
                        ),
                        Arc(
                            from = 2f/3f,
                            to = 1f,
                            color = Color.Blue,
                        ),
                    )
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                GlassPanel(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                )
            }
        }
    }
}