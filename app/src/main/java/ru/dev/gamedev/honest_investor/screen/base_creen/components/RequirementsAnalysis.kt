package ru.dev.gamedev.honest_investor.screen.base_creen.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import ru.dev.gamedev.honest_investor.R
import ru.dev.gamedev.honest_investor.utils.Point
import ru.dev.gamedev.honest_investor.utils.listPointDown
import ru.dev.gamedev.honest_investor.utils.listPointUp
import kotlin.math.roundToInt


@Composable
fun RequirementsAnalysis(imageResId: Int,
                         width : Int,
                         height : Int,
                         showAnalytics: Boolean = false, upGraps : Boolean = true) {


    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        // Rounded container with a grid
        Box(
            modifier = Modifier
                .border(2.dp, color = Color.White, shape = RoundedCornerShape(18.dp))
                .fillMaxWidth(1f)
                .height(280.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ) {
            Grid(imageResId, showAnalytics = showAnalytics,
                width = width,
                height= height,
                upGraps = upGraps)
        }
    }
}




@Composable
fun Grid(imageResId: Int,
         width : Int,
         height : Int,
         showAnalytics: Boolean,
         upGraps : Boolean = true) {
    val values = if(upGraps) listPointUp.random() else listPointDown.random()
    val drawColor = if(!upGraps) Color(0xFFA6310C) else Color(0xFF47A60C)

    val context = LocalContext.current
    val local = LocalDensity.current.density


    val imageDrawable = remember(imageResId) {
        context.resources.getDrawable(imageResId, null)
    }

    val bitmap = remember(imageDrawable) {
        imageDrawable.toBitmap(
            width = (width * local).roundToInt(),
            height = (height * local).roundToInt()
        )
    }.asImageBitmap()



    val highestValueInMiddle = values.find { it.point == true }?.y ?: 0f


    val highestValueInMiddleX = values.find { it.point == true }?.x ?: 0f



    // find max and min value of X, we will need that later
    val minXValue = 0f
    val maxXValue = 14f

    // find max and min value of Y, we will need that later
    val minYValue = 0f
    val maxYValue = 14f

    // Animatable to control the progress of the animation
    val progress = remember { Animatable(0f) }

    LaunchedEffect(showAnalytics) {
        if(!showAnalytics) return@LaunchedEffect
        progress.animateTo(1f, animationSpec = tween(1000)) // adjust the duration as needed
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(280.dp)
            .drawBehind {
                // draw grid
                val rows = 14
                val cols = 14

                val cellWidth = size.width / cols
                val cellHeight = size.height / rows

                // Draw horizontal lines
                for (i in 1 until rows) {
                    drawLine(
                        color = Color.White.copy(alpha = .6f),
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(x = 0f, y = i * cellHeight),
                        end = Offset(x = size.width, y = i * cellHeight)
                    )
                }

                // Draw vertical lines
                for (i in 1 until cols) {
                    drawLine(
                        color = Color.White.copy(alpha = .6f),
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(x = i * cellWidth, y = 0f),
                        end = Offset(x = i * cellWidth, y = size.height)
                    )
                }
                if (showAnalytics) {
                    // Calculate the current progress based on the animation
                    val currentProgress = progress.value

                    val pixelPoints = values.map {
                        val x = it.x.toFloat().mapValueToDifferentRange(
                            inMin = minXValue,
                            inMax = maxXValue,
                            outMin = 0f,
                            outMax = size.width
                        ) * currentProgress

                        val y = it.y.toFloat().mapValueToDifferentRange(
                            inMin = minYValue,
                            inMax = maxYValue,
                            outMin = size.height,
                            outMax = 0f
                        ) * currentProgress

                        Point(x.toDouble(), y.toDouble())
                    }

                    val path = Path()
                    // Fill the path based on the current progress
                    pixelPoints
                        .take((pixelPoints.size * currentProgress).toInt())
                        .forEachIndexed { index, point ->
                            if (index == 0) {
                                path.moveTo(point.x.toFloat(), point.y.toFloat())
                            } else {
                                path.lineTo(point.x.toFloat(), point.y.toFloat())
                            }
                        }


                    drawPath(
                        path,
                        color = drawColor,
                        style = Stroke(width = 8f)
                    )


                    // Draw circle at Point(5f, 10f)
                    val circleX = highestValueInMiddleX.toFloat().mapValueToDifferentRange(
                        minXValue,
                        maxXValue,
                        0f,
                        size.width
                    ) * currentProgress
                    val circleY = highestValueInMiddle.toFloat().mapValueToDifferentRange(
                        minYValue,
                        maxYValue,
                        size.height,
                        0f
                    ) * currentProgress

                    // Check if the animation is complete before displaying the point and image
                    if (currentProgress == 1f) {
                        // Draw circle at the desired point

                        drawCircle(
                            color = drawColor,
                            center = Offset(circleX, circleY),
                            radius = 8.dp.toPx()
                        )


                        drawCircle(
                            color = Color.White,
                            center = Offset(circleX, circleY),
                            radius = 8.dp.toPx(), // Adjust the border width as needed
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
                            alpha = 1f
                        )

                        drawImage(
                            image = bitmap,
                            alpha = 1f,
                            topLeft = Offset(
                                circleX - bitmap.width / 2f,
                                (circleY - bitmap.height / 2f) - 100f
                            )
                        )
                    }


                }
            }
    )
}


fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin