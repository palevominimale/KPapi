package com.junopark.kpapi.app.navigation

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem("splash")
    object List : NavigationItem("list")
    object ListFlow : NavigationItem("listflow")
    object Single : NavigationItem("single")
    object Favs : NavigationItem("favs")
    object Error : NavigationItem("error")
}