package com.aiassistant

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SiriGradientBorder(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    borderWidth: Dp = 4.dp,
    colors: List<Color> = listOf(
        Color(0xFF00C6FF),
        Color(0xFF0072FF),
        Color(0xFF9D50BB),
        Color(0xFFFF006E),
        Color(0xFF00C6FF)
    )
) {
    val infiniteTransition = rememberInfiniteTransition(label = "border")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isActive) 1f else 0f,
        animationSpec = tween(300),
        label = "alpha"
    )

    if (alpha > 0f) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2f
            val centerY = canvasHeight / 2f
            
            val brush = Brush.sweepGradient(
                colors = colors,
                center = Offset(centerX, centerY)
            )

            rotate(rotation, pivot = Offset(centerX, centerY)) {
                drawRoundRect(
                    brush = brush,
                    size = Size(canvasWidth, canvasHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(24.dp.toPx()),
                    style = Stroke(width = borderWidth.toPx()),
                    alpha = alpha
                )
            }
        }
    }
}

@Composable
fun SiriSweepEffect(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    colors: List<Color> = listOf(
        Color(0x00FFFFFF),
        Color(0x66FFFFFF),
        Color(0xCCFFFFFF),
        Color(0x66FFFFFF),
        Color(0x00FFFFFF)
    )
) {
    var animationPlayed by remember { mutableStateOf(false) }
    
    val offsetY by animateFloatAsState(
        targetValue = if (isActive && animationPlayed) 0f else 1f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "sweep",
        finishedListener = {
            if (isActive) {
                animationPlayed = true
            }
        }
    )

    LaunchedEffect(isActive) {
        if (isActive) {
            animationPlayed = false
        }
    }

    if (isActive) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val canvasHeight = size.height
            val sweepHeight = canvasHeight * 0.3f
            val currentY = canvasHeight - (canvasHeight * (1f - offsetY))

            val gradient = Brush.verticalGradient(
                colors = colors,
                startY = currentY - sweepHeight / 2,
                endY = currentY + sweepHeight / 2
            )

            drawRect(
                brush = gradient,
                topLeft = Offset(0f, currentY - sweepHeight / 2),
                size = Size(size.width, sweepHeight),
                blendMode = BlendMode.Plus
            )
        }
    }
}

@Composable
fun SiriAnimatedBackground(
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset1"
    )

    val offset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset2"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isActive) 0.3f else 0f,
        animationSpec = tween(500),
        label = "bgAlpha"
    )

    if (alpha > 0f) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1A1A2E).copy(alpha = alpha),
                            Color(0xFF0F0F1E).copy(alpha = alpha),
                            Color(0xFF000000).copy(alpha = alpha)
                        ),
                        center = Offset(
                            offset1 * 1000f,
                            offset2 * 1000f
                        )
                    )
                )
        )
    }
}

@Composable
fun PulsingGlow(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    color: Color = Color(0xFF00C6FF)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    if (isActive) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = (size.width.coerceAtMost(size.height) / 4f) * scale

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = alpha),
                        Color.Transparent
                    ),
                    center = Offset(centerX, centerY),
                    radius = radius
                ),
                radius = radius,
                center = Offset(centerX, centerY),
                blendMode = BlendMode.Plus
            )
        }
    }
}
