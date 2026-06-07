package com.example.messenger.ui.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val PrimeShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

object PrimeShapeTokens {
    val bubbleSent = RoundedCornerShape(
        topStart = 18.dp,
        topEnd = 18.dp,
        bottomEnd = 4.dp,
        bottomStart = 18.dp
    )
    val bubbleReceived = RoundedCornerShape(
        topStart = 18.dp,
        topEnd = 18.dp,
        bottomEnd = 18.dp,
        bottomStart = 4.dp
    )
    val avatar = RoundedCornerShape(50)
    val chip = RoundedCornerShape(20.dp)
    val card = RoundedCornerShape(16.dp)
    val input = RoundedCornerShape(24.dp)
    val button = RoundedCornerShape(12.dp)
}
