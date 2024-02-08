package ru.dev.gamedev.honest_investor.navigation.navs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation

import ru.dev.gamedev.honest_investor.AppState
import ru.dev.gamedev.honest_investor.navigation.AppRoutes
import ru.dev.gamedev.honest_investor.navigation.Graph
import ru.dev.gamedev.honest_investor.screen.Screen1
import ru.dev.gamedev.honest_investor.screen.Screen2
import ru.dev.gamedev.honest_investor.screen.Screen3
import ru.dev.gamedev.honest_investor.screen.Screen4
import ru.dev.gamedev.honest_investor.screen.Screen5
import ru.dev.gamedev.honest_investor.screen.Screen6
import ru.dev.gamedev.honest_investor.screen.StartScreen

import ru.dev.gamedev.honest_investor.utils.createTransitionComposableArg



fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(Graph.Home.graph, navOptions)
}

fun NavGraphBuilder.homeNav(appState: AppState) {
    navigation(
        route = Graph.Home.graph,
        startDestination = AppRoutes.START.route
    ) {
        createTransitionComposableArg(
            route =AppRoutes.START.route,
        ){
            StartScreen(appState = appState)
        }
        createTransitionComposableArg(
            route = AppRoutes.SCREEN1.route,
        ){
            Screen1(appState = appState)
        }
        createTransitionComposableArg(
            route = AppRoutes.SCREEN2.route,
        ){
            Screen2(appState = appState)
        }
        createTransitionComposableArg(
            route = AppRoutes.SCREEN3.route,
        ){
            Screen3(appState = appState)
        }
        createTransitionComposableArg(
            route = AppRoutes.SCREEN4.route,
        ){
            Screen4(appState = appState)
        }
        createTransitionComposableArg(
            route = AppRoutes.SCREEN5.route,
        ){
            Screen5(appState = appState)
        }
        createTransitionComposableArg(
            route = AppRoutes.SCREEN6.route,
        ){
            Screen6(appState = appState)
        }
    }
}