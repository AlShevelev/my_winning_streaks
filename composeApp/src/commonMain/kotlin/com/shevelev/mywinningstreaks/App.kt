package com.shevelev.mywinningstreaks

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
import com.shevelev.mywinningstreaks.shared.ui.circlediagram.Arc
import com.shevelev.mywinningstreaks.shared.ui.circlediagram.CircleDiagram
import com.shevelev.mywinningstreaks.shared.ui.theme.MyWinningStreaksTheme
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.background
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MyWinningStreaksTheme {
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
}