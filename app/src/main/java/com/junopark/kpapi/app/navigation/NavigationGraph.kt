package com.junopark.kpapi.app.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.app.ui.screens.*
import com.junopark.kpapi.app.ui.screens.list.FlowListScreen
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.prefs.PrefsDTO

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: UiState,
    reducer: (UiIntent) -> Unit,
    setPrefs: (PrefsDTO) -> Unit = {},
    share: (FilmItemBig) -> Unit = {},
    favorite: (FilmItemBig) -> Unit = {}
) {

    NavHost(
        navController = navController,
        startDestination = NavigationItem.ListFlow.route
    ) {

        composable(NavigationItem.Single.route) {
            when(state) {
                is UiState.IsLoading -> FilmDescriptionScreen(modifier = modifier)
                is UiState.Ready.Single -> FilmDescriptionScreen(
                    modifier = modifier,
                    item = state.data,
                    onBack = {
                        reducer(UiIntent.Navigate.Back)
                        navController.popBackStack()
                    }
                )
                else -> {}
            }
        }

        composable(NavigationItem.Favs.route) {
//            when(state) {
//                is UiState.IsLoading -> ListScreen(modifier = modifier)
//                is UiState.Ready.FilmList -> ListScreen(modifier = modifier, items = state.data,
//                    state = listState)
//                else -> {}
//            }
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

        composable(NavigationItem.ListFlow.route) {
            val flowListState = rememberLazyListState(0,0)
            when(state) {
                is UiState.Ready.FilmListFlow -> {
                    val list = state.data.collectAsLazyPagingItems()
                    FlowListScreen(
                        modifier = modifier,
                        items = list,
                        state = flowListState,
                        onSelect = { reducer(UiIntent.Search.ById(it)) },
                        share = { share(it) },
                        favorite = { favorite(it) }
                    )
                setPrefs(state.prefs)
                }
                else -> {}
            }
        }
    }

    when(state) {
        is UiState.IsLoading -> {}
        is UiState.Ready.Favorites ->       navController.navigate(NavigationItem.List.route)       { launchSingleTop = true }
//        is UiState.Ready.FilmList ->        navController.navigate(NavigationItem.List.route)       { launchSingleTop = true }
        is UiState.Ready.FilmListFlow ->    navController.navigate(NavigationItem.ListFlow.route)   { launchSingleTop = true }
        is UiState.Ready.Single ->          navController.navigate(NavigationItem.Single.route)     { launchSingleTop = true }
        is UiState.Error.HttpError ->       navController.navigate(NavigationItem.Error.route)      { launchSingleTop = true }
        is UiState.Error.Exception ->       navController.navigate(NavigationItem.Error.route)      { launchSingleTop = true }
        is UiState.Error.NoInternet ->      navController.navigate(NavigationItem.Error.route)      { launchSingleTop = true }
        is UiState.Ready.Empty ->           navController.navigate(NavigationItem.Error.route)      { launchSingleTop = true }
        else -> {}
    }
}