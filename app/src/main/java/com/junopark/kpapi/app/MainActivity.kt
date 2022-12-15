package com.junopark.kpapi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junopark.kpapi.app.ui.screens.bottomsheet.FilterPad
import com.junopark.kpapi.app.ui.theme.KPapiTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
            )
            val coroutineScope = rememberCoroutineScope()
            KPapiTheme {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 72.dp,
                    sheetContent = { FilterPad() }
                ) {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun DefaultPreview() {
    KPapiTheme {
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        val scope = rememberCoroutineScope()
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 72.dp,
            sheetContent = { FilterPad(
                switchFilter = {
                    scope.launch {
                        if (scaffoldState.bottomSheetState.isCollapsed) {
                            scaffoldState.bottomSheetState.expand()
                        } else {
                            scaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
            ) }
        ) {

        }
    }
}