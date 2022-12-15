package com.junopark.kpapi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.TopBar
import com.junopark.kpapi.app.ui.screens.FilterPad
import com.junopark.kpapi.app.ui.screens.SplashScreen
import com.junopark.kpapi.app.ui.theme.KPapiTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = vm.uiState.collectAsState()
            KPapiTheme {
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                )
                val scope = rememberCoroutineScope()
                if(state.value == UiState.Splash) {
                    SplashScreen()
                } else {
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetPeekHeight = 72.dp,
                        topBar = { TopBar(label = "KP Api test App") },
                        sheetContent = { FilterPad(
                            switchFilter = {
                                scope.launch {
                                    if (scaffoldState.bottomSheetState.isCollapsed) {
                                        scaffoldState.bottomSheetState.expand()
                                    } else {
                                        scaffoldState.bottomSheetState.collapse()
                                    }
                                }
                            },
                            updateFilter = { vm.reduce(UiIntent.Filter.Set(it)) },
                            searchByString = { vm.reduce(UiIntent.Search.ByName(it)) },
                            searchByFilter = { vm.reduce(UiIntent.Search.ByFilter)}
                        ) }
                    ) {
                        when(state.value) {
                            is UiState.IsLoading -> {}
                            is UiState.Error.NoInternet -> {}
                            is UiState.Ready.Empty -> {}

                            is UiState.Ready.Favorites -> {}
                            is UiState.Ready.List -> {
                            }
                            is UiState.Ready.Single -> {}
                            is UiState.Error.HttpError -> {}
                            is UiState.Error.Exception -> {}

                            else -> {}
                        }
                    }
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
            topBar = { TopBar(label = "KP Api test App") },
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