package com.junopark.kpapi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.junopark.kpapi.app.navigation.NavigationGraph
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.*
import com.junopark.kpapi.app.ui.theme.KPapiTheme
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem
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
                var countries by remember { mutableStateOf(listOf(CountryItem())) }
                var genres by remember { mutableStateOf(listOf(GenreItem())) }
                val scope = rememberCoroutineScope()
                val listState = LazyListState(0)
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
                        NavigationGraph(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            state = state.value,
                            reducer = { intent -> vm.reduce(intent) },
                            listState = listState,
                            setPrefs = { prefs ->
                                genres = prefs.genres ?: emptyList()
                                countries = prefs.countries ?: emptyList()
                            }
                        )
                    }
                }
            }
        }
    }
}