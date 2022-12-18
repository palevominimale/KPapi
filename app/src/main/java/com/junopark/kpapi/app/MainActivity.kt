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
import androidx.navigation.compose.rememberNavController
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.*
import com.junopark.kpapi.app.ui.theme.KPapiTheme
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem
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
            val navController = rememberNavController()

            KPapiTheme {
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                )
                var filter by remember { mutableStateOf(FilmFilter()) }
                var countries by remember { mutableStateOf(listOf(CountryItem())) }
                var genres by remember { mutableStateOf(listOf(GenreItem())) }
                val scope = rememberCoroutineScope()

                if(state.value == UiState.Splash) {
                    SplashScreen()
                } else {
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetPeekHeight = 72.dp,
                        topBar = { TopBar(label = "KP Api test App") },
                        sheetContent = { FilterPad(
                            switchFilter = { scope.launch {
                                    if (scaffoldState.bottomSheetState.isCollapsed) {
                                        scaffoldState.bottomSheetState.expand()
                                    } else scaffoldState.bottomSheetState.collapse()
                                }},
                            updateFilter = { vm.reduce(UiIntent.Filter.Set(it)) },
                            searchByString = { vm.reduce(UiIntent.Search.ByName(it)) },
                            searchByFilter = { vm.reduce(UiIntent.Search.ByFilter)},
                            genres = genres,
                            countries = countries
                        ) }
                    ) {
                        Box(
                            modifier = Modifier.padding(it)
                        ) {
                            when(state.value) {
                                is UiState.IsLoading -> {}
                                is UiState.Error.NoInternet -> NoInternetScreen()
                                is UiState.Ready.Empty -> ErrorScreen(code = 0, message = "Ничего не найдено")

                                is UiState.Ready.Favorites -> {
                                    filter = (state.value as UiState.Ready.Favorites).prefs.filter ?: FilmFilter()
                                    if((state.value as UiState.Ready.Favorites).prefs.genres != null) {
                                        genres = (state.value as UiState.Ready.Favorites).prefs.genres!!
                                    }
                                    if((state.value as UiState.Ready.Favorites).prefs.countries != null) {
                                        countries = (state.value as UiState.Ready.Favorites).prefs.countries!!
                                    }
                                }
                                is UiState.Ready.FilmList -> {
                                    filter = (state.value as UiState.Ready.FilmList).prefs.filter ?: FilmFilter()
                                    if((state.value as UiState.Ready.FilmList).prefs.genres != null) {
                                        genres = (state.value as UiState.Ready.FilmList).prefs.genres!!
                                    }
                                    if((state.value as UiState.Ready.FilmList).prefs.countries != null) {
                                        countries = (state.value as UiState.Ready.FilmList).prefs.countries!!
                                    }
                                    ListScreen(
                                        items = (state.value as UiState.Ready.FilmList).data
                                    )
                                }
                                is UiState.Ready.Single -> {
                                    filter = (state.value as UiState.Ready.Single).prefs.filter ?: FilmFilter()
                                    if((state.value as UiState.Ready.Single).prefs.genres != null) {
                                        genres = (state.value as UiState.Ready.Single).prefs.genres!!
                                    }
                                    if((state.value as UiState.Ready.Single).prefs.countries != null) {
                                        countries = (state.value as UiState.Ready.Single).prefs.countries!!
                                    }
                                }

                                is UiState.Error.HttpError -> ErrorScreen(
                                        code = (state.value as UiState.Error.HttpError).code,
                                        message = (state.value as UiState.Error.HttpError).message
                                    )
                                is UiState.Error.Exception -> ErrorScreen(e = (state.value as UiState.Error.Exception).e)
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