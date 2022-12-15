package com.junopark.kpapi.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junopark.kpapi.app.ui.theme.Typography

@Composable
@Preview
fun TopBar(
    modifier: Modifier = Modifier,
    label: String = "label",
) {
    Surface(
        color = Color.White,
        elevation = 5.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = Typography.bodyLarge)
        }
    }
}