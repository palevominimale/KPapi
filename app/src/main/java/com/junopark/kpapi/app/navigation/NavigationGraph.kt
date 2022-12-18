package com.junopark.kpapi.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.ListScreen
import com.junopark.kpapi.app.ui.screens.SplashScreen
import com.junopark.kpapi.app.ui.screens.TYPE

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: UiState,
    reducer: (UiIntent) -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen()
        }
        composable(NavigationItem.List.route) {
            val localState = state as UiState.Ready.FilmList
            ListScreen(
                modifier = modifier,
                items = localState.data,
            )
        }
        composable(NavigationItem.Single.route) {
//            SingleScreen()
        }
        composable(NavigationItem.Favs.route) {
//            FavsScreen()
        }
    }
}