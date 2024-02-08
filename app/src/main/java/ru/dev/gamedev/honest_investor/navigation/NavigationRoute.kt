package ru.dev.gamedev.honest_investor.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import ru.dev.gamedev.honest_investor.AppState
import ru.dev.gamedev.honest_investor.R
import ru.dev.gamedev.honest_investor.navigation.navs.homeNav


@Composable
fun Navigation(
    appState: AppState,
    startDestination: String
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = stringResource(R.string.not_connected)

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    NavHost(
        navController = appState.navController,
        route = Graph.Root.graph,
        startDestination = startDestination
    ) {

        homeNav(
            appState = appState,
        )

    }

}