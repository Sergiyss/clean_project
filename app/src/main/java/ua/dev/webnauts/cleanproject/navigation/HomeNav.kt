package ua.dev.webnauts.cleanproject.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.dev.webnauts.cleanproject.navigation.settings_navigation.NavRoutes
import ua.dev.webnauts.cleanproject.AppState
import ua.dev.webnauts.cleanproject.navigation.settings_navigation.Graph
import ua.dev.webnauts.cleanproject.screen.home.HomeScreen
import ua.dev.webnauts.cleanproject.utils.createTransitionComposableArg

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNav(appState: AppState) {
    navigation(
        route = Graph.Home.graph,
        startDestination = NavRoutes.Home().route
    ) {
        createTransitionComposableArg(
            route = NavRoutes.Home().route,
        ){
            HomeScreen(appState = appState)
        }
    }
}