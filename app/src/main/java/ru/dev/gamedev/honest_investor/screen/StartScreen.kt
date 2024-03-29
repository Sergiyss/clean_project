package ru.dev.gamedev.honest_investor.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dev.gamedev.honest_investor.AppState
import ru.dev.gamedev.honest_investor.R
import ru.dev.gamedev.honest_investor.navigation.AppRoutes


@Composable
fun StartScreen(appState: AppState){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.fio), contentDescription = "",
            modifier = Modifier.size(90.dp))


        Icon(painter = painterResource(id = R.drawable.name_app), contentDescription = "",
            modifier = Modifier.size(210.dp).offset(y = -70.dp), tint = Color.White)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 40.dp)
                .fillMaxWidth(1f),
            shape =  RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFFFFFFFF),
                contentColor =  Color(0xFF000000),
            ),
            onClick = {
            appState.navController.navigate(AppRoutes.SCREEN1.route)
        }) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Продолжить",
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontStyle = FontStyle.Normal
                )
            )
        }

        Spacer(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(1f))
    }
}