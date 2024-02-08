package ru.dev.gamedev.honest_investor.screen.base_creen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.dev.gamedev.honest_investor.R

@Composable
fun BackgroundBox(
    content: @Composable() (BoxScope.() -> Unit)
){

    Box(modifier = Modifier
        .fillMaxSize(1f)
        .background(color = Color.Black)
        .paint(
            // Replace with your image id
            painterResource(id = R.drawable.bacgraund),
            contentScale = ContentScale.FillBounds
        )
        ){

        content()

        Image(painter = painterResource(id = R.drawable.neon_blue), contentDescription = "neon",
            modifier = Modifier.align(Alignment.TopStart).offset(y = -20.dp, x = -50.dp)
                .size(600.dp)
                .alpha(.9f)
                .zIndex(-1f),
            contentScale = ContentScale.Crop
        )

        Image(painter = painterResource(id = R.drawable.neon_lite_blue), contentDescription = "neon",
            modifier = Modifier.align(Alignment.BottomEnd).offset(y = 10.dp, x = 90.dp)
                .size(400.dp)
                .alpha(.9f)
                .zIndex(-1f),
            contentScale = ContentScale.Crop
        )
    }
}