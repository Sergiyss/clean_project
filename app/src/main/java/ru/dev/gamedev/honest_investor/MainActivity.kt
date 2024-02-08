package ru.dev.gamedev.honest_investor

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import ru.dev.gamedev.honest_investor.navigation.Graph
import ru.dev.gamedev.honest_investor.navigation.Navigation
import ru.dev.gamedev.honest_investor.network.network_monitor.NetworkMonitor
import ru.dev.gamedev.honest_investor.screen.base_creen.components.BackgroundBox
import ru.dev.gamedev.honest_investor.ui.theme.CleanProjectTheme
import ru.dev.gamedev.honest_investor.utils.AdvertisingInfo
import ru.dev.gamedev.honest_investor.utils.PreferencesManager
import ru.dev.gamedev.honest_investor.workers.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
/***
 *  Проект с одним модулем...
 *
 *  Для быстрого старта, где уже есть всё необходимое, чтобы начать программировать
 *  и накидывать дизайн, не отвлекаясь на подключение либ и настройки проекта, навигации
 * */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    lateinit var preferencesManager: PreferencesManager

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = PreferencesManager(this)

        setContent {
            val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

            if (permissionState.status.isGranted && permissionState.status.shouldShowRationale) {
               LaunchedEffect(key1 = Unit, block = { permissionState.launchPermissionRequest() })
            }

            AdvertisingInfo(this).getAdvertisingId()

            CleanProjectTheme {
                BackgroundBox{
                    RootScreen(networkMonitor = networkMonitor, startDestination = Graph.Home.graph)
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    override fun onPause() {
        super.onPause()
        println("ON DESTROY PAUSE ${preferencesManager.getData("finishScreen", "")}")
    }
}

/***
 * Стартовая точка для [rootNavigation]
 * Здесь будут всё основные экраны. Отделил по той причине, что хочу [WelcomeScreen]
 * вынести из навигации вверх. Во многих моментах она будет только мешать.
 * */
@Composable
fun RootScreen(networkMonitor: NetworkMonitor, startDestination : String){
    val appState  = rememberAppState(networkMonitor = networkMonitor,)
    var selectedItem by remember { mutableStateOf(0) }


    appState.showSnackBar("hi", duration = androidx.compose.material.SnackbarDuration.Short)

    /**
     * @param startDestination - start destination for navigation
     * @param token - token for user,
     * @param startDestination start Screen
     * */
    Navigation(
        appState = appState,
        startDestination = startDestination
    )
}