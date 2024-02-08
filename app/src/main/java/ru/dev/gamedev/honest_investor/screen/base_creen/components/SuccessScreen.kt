package ru.dev.gamedev.honest_investor.screen.base_creen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dev.gamedev.honest_investor.ui.compose_components.TextField.TextFieldWithTitle
import ru.dev.gamedev.honest_investor.utils.PreferencesManager
import ru.dev.gamedev.personal_analyst.ui.compose_components.photo_text_field.Country
import ru.dev.gamedev.personal_analyst.ui.compose_components.photo_text_field.PhoneTextField


@Composable
fun SuccessScreen(success:()->Unit,) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber  by remember { mutableStateOf( TextFieldValue() ) }


    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }


    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Поздравляем".uppercase(),
                color = Color.Black,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 19.sp,
                    lineHeight = 23.sp,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                text = "У вас прекрасное чувство рынка.\nЧтобы получить 10 подарочных акций завершите регистрацию",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    lineHeight = 19.sp,
                    fontStyle = FontStyle.Normal
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextFieldWithTitle(
                placeholder = {
                    Text(text = "Ваше имя",
                        color = Color.Black.copy(alpha = .3f),
                        style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                            fontStyle = FontStyle.Italic
                        ))
                },
                error = !(name.length > 3),
                value = name,
                onValueChange = {
                    name = it
                }
            )
            TextFieldWithTitle(
                placeholder = {
                    Text(text = "Ваша фамилия",
                        color = Color.Black.copy(alpha = .3f),
                        style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                            fontStyle = FontStyle.Italic
                        ))
                },
                error = !(lastName.length > 3),
                value = lastName,
                onValueChange = {
                    lastName = it
                }
            )
            PhoneTextField(
                value = phoneNumber,
                context = context,
                onValueChange = {
                        phone, country ->
                    phoneNumber = phone
                },
                onCountryChange = {
                        phone, country ->
                    phoneNumber = phone
                },
                countries = listOf(
                    Country(99,
                        "ru", "+7" )
                ),
                error = phoneNumber.text.length < 12,
                placeholder = {
                    Text(text = "Ваш номер телефона",
                        color = Color.Black.copy(alpha = .3f),
                        style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                            fontStyle = FontStyle.Italic
                        ))
                },
            )

            Button(
                modifier = Modifier.fillMaxWidth(1f).padding(top = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF250AB4),
                    disabledContainerColor = Color(0xFF250AB4).copy(alpha = .5f),
                    disabledContentColor = Color(0xFFFFFFFF).copy(alpha = .7f)
                ),
                enabled = phoneNumber.text.length > 11 && lastName.length > 3 && name.length > 3,
                onClick = {
                    preferencesManager.saveData("finishScreen", "okey")
                    success()
                }) {

                androidx.compose.material3.Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Зарегистрироваться", style = TextStyle(
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        lineHeight = 19.sp,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
        }


    }
}
