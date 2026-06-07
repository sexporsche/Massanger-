package com.example.messenger.ui.compose.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.messenger.ui.compose.theme.PrimeSpacing

fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )
    val base = MaterialTheme.colorScheme.surfaceVariant
    val highlight = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    background(
        brush = Brush.linearGradient(
            colors = listOf(base, highlight, base),
            start = Offset(translate - 300f, translate - 300f),
            end = Offset(translate, translate)
        )
    )
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .shimmerEffect()
    )
}

@Composable
fun ChatListSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(PrimeSpacing.lg),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(PrimeSpacing.md)
    ) {
        repeat(6) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShimmerBox(modifier = Modifier.size(52.dp), cornerRadius = 26.dp)
                Spacer(Modifier.width(PrimeSpacing.md))
                Column(Modifier.weight(1f)) {
                    ShimmerBox(modifier = Modifier.fillMaxWidth(0.6f).height(16.dp))
                    Spacer(Modifier.height(PrimeSpacing.sm))
                    ShimmerBox(modifier = Modifier.fillMaxWidth(0.85f).height(12.dp))
                }
            }
        }
    }
}

@Composable
fun NewsListSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(PrimeSpacing.lg),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(PrimeSpacing.md)
    ) {
        repeat(4) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                cornerRadius = 16.dp
            )
        }
    }
}

@Composable
fun ProfileSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(PrimeSpacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerBox(modifier = Modifier.size(96.dp), cornerRadius = 48.dp)
        Spacer(Modifier.height(PrimeSpacing.lg))
        ShimmerBox(modifier = Modifier.fillMaxWidth(0.5f).height(24.dp))
        Spacer(Modifier.height(PrimeSpacing.sm))
        ShimmerBox(modifier = Modifier.fillMaxWidth(0.7f).height(16.dp))
        Spacer(Modifier.height(PrimeSpacing.xxl))
        repeat(3) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(vertical = PrimeSpacing.xs),
                cornerRadius = 16.dp
            )
        }
    }
}
