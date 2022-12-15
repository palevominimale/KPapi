package com.junopark.kpapi.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junopark.kpapi.app.R

@Composable
@Preview
fun ErrorScreen(
    code: Int? = null,
    message: String? = null,
    e: Throwable? = null,
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                shadowElevation = 15.dp,
                color = Color.White,
                modifier = Modifier.size(220.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_error_outline_24),
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(150.dp)
                    )
                }
            }
            if(message != null) {
                Text(text = "Ошибка: $message")
            }
            if(code != null) {
                Text(text = "Код: $code")
            }
            if(e != null) {
                Text(text = "Фатальная ошибка: ${e.message}")
                Text(text = "Stack trace: ${e.stackTraceToString()}")
            }
        }
    }
}