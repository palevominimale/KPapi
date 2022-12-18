package com.junopark.kpapi.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.*
import kotlinx.coroutines.flow.last

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
            when(state) {
                is UiState.IsLoading -> ListScreen(modifier = modifier)
                is UiState.Ready.FilmList -> ListScreen(
                    modifier = modifier,
                    items = state.data,
                    onBack = { reducer(UiIntent.Navigate.Back) },
                    onSelect = { id ->
                        navController.navigate(NavigationItem.Single.route)
                        reducer(UiIntent.Search.ById(id))
                    }
                )
                else -> {}
            }
        }

        composable(NavigationItem.Single.route) {
            when(state) {
                is UiState.IsLoading -> FilmDescriptionScreen(modifier = modifier)
                is UiState.Ready.Single -> FilmDescriptionScreen(modifier = modifier, item = state.data)
                else -> {}
            }
        }

        composable(NavigationItem.Favs.route) {
            when(state) {
                is UiState.IsLoading -> ListScreen(modifier = modifier)
                is UiState.Ready.FilmList -> ListScreen(modifier = modifier, items = state.data)
                else -> {}
            }
        }
        composable(NavigationItem.Error.route) {
            when(state) {
                is UiState.Error.NoInternet -> NoInternetScreen(
                        onBack = { navController.navigate(navController.currentBackStackEntry?.id ?: "splash") }
                    )
                is UiState.Error.HttpError -> ErrorScreen(state.code, state.message)
                is UiState.Error.Exception -> ErrorScreen(e = state.e)
                else -> {}
            }
        }
    }
}