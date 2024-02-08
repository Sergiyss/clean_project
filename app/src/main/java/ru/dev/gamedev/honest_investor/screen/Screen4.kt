package ru.dev.gamedev.honest_investor.screen

import androidx.compose.runtime.Composable
import ru.dev.gamedev.honest_investor.AppState

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.dev.gamedev.honest_investor.R
import ru.dev.gamedev.honest_investor.navigation.AppRoutes
import ru.dev.gamedev.honest_investor.screen.base_creen.components.Questionnaire
import ru.dev.gamedev.honest_investor.screen.base_creen.components.RequirementsAnalysis
import ru.dev.gamedev.honest_investor.screen.base_creen.components.TitleQues

@Composable
fun Screen4(appState: AppState){
    var showAnalytics  by remember { mutableStateOf( false ) }
    var upGraps by remember { mutableStateOf( true ) }
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = true) {}
    Box(Modifier.fillMaxSize(1f)) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
                .fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {

                TitleQues(
                    title = "В прошлую пятницу рынок узнал о снижении дивидендов акций банка Санкт-Петербург с 16.5% до 13,5%."
                )
                RequirementsAnalysis(R.drawable.sankt,
                    width = 120,
                    height = 40,
                    showAnalytics, upGraps = upGraps)
                Questionnaire(
                    title = "Вопрос 4/4",
                    subTitle = "Куда направится цена акций BSPB (Банк Санкт-Петербург) ?",

                    up={
                        showAnalytics = true
                        upGraps = true
                        coroutineScope.launch {
                            delay(2000)
                            appState.navController.navigate(AppRoutes.SCREEN5.route)
                        }
                }, down={
                    showAnalytics = true
                    upGraps = false
                    coroutineScope.launch {
                        delay(2000)
                        appState.navController.navigate(AppRoutes.SCREEN5.route)
                    }
                })
            }
        }
    }
}