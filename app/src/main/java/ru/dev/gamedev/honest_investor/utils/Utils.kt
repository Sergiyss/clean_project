package ru.dev.gamedev.honest_investor.utils

import android.content.Context
import android.telephony.TelephonyManager
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Locale


fun getCountryCode(context: Context): String {
    val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val countryCode = telephonyManager.networkCountryIso
    return countryCode.toUpperCase(Locale.getDefault())
}


fun getIpv4HostAddress(): String {
    NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
        networkInterface.inetAddresses?.toList()?.find {
            !it.isLoopbackAddress && it is Inet4Address
        }?.let { return it.hostAddress }
    }
    return ""
}

/**
 * Переход по навигации со следующем очищением стека
 * */
fun NavController.navigateAndClean(route: String) {
    navigate(route = route) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
    }
}
/**
 * Переход по навигации, уже включен в себя [launchSingleTop] true
 * */
fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavGraphBuilder.createTransitionComposableArg(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        arguments = arguments

    ) {
        content(it)
    }
}