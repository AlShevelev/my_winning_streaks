package com.shevelev.mywinningstreaks.shared.ui

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TextWithIcon(
    text: String,
    icon: DrawableResource,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    val myId = "inlineContent"
    val text = buildAnnotatedString {
        append(text)
        append(" ")
        appendInlineContent(myId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = style.fontSize,
                    height = style.fontSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                )
            }
        )
    )

    Text(
        text = text,
        style = style,
        modifier = modifier,
        inlineContent = inlineContent,
    )
}