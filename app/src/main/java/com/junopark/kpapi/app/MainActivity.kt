package com.junopark.kpapi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.*
import com.junopark.kpapi.app.ui.theme.KPapiTheme
import com.junopark.kpapi.entities.ListResponse
import com.junopark.kpapi.entities.filter.FilmFilter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.reduce(UiIntent.Show.Top)
        setContent {
            val state = vm.uiState.collectAsState()
            KPapiTheme {
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                )
                var filter by remember { mutableStateOf(FilmFilter()) }
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
                        Box(
                            modifier = Modifier.padding(it)
                        ) {
                            when(state.value) {
                                is UiState.IsLoading -> {}
                                is UiState.Error.NoInternet -> { NoInternetScreen() }
                                is UiState.Ready.Empty -> {
                                    ErrorScreen(e = Throwable(message = "Ничего не найдено"))
                                }

                                is UiState.Ready.Favorites -> {
                                    filter = (state.value as UiState.Ready.Favorites).filter
                                }
                                is UiState.Ready.FilmList -> {
                                    filter = (state.value as UiState.Ready.FilmList).filter
                                    ListScreen(
                                        items = (state.value as UiState.Ready.FilmList).data
                                    )
                                }
                                is UiState.Ready.Single -> {
                                    filter = (state.value as UiState.Ready.Single).filter
                                }

                                is UiState.Error.HttpError -> {
                                    ErrorScreen(
                                        code = (state.value as UiState.Error.HttpError).code,
                                        message = (state.value as UiState.Error.HttpError).message
                                    )
                                }
                                is UiState.Error.Exception -> {
                                    ErrorScreen(e = (state.value as UiState.Error.Exception).e)
                                }

                                else -> {}
                            }
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