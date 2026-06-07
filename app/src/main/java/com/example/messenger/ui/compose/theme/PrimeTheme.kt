package com.example.messenger.ui.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.example.messenger.domain.repository.PrimeSettingsRepository

val DarkColors = darkColorScheme(
    primary = PrimeColors.Primary,
    onPrimary = PrimeColors.TextPrimary,
    primaryContainer = Color(0xFF1D4ED8),
    onPrimaryContainer = PrimeColors.TextPrimary,
    secondary = PrimeColors.Secondary,
    onSecondary = PrimeColors.Background,
    secondaryContainer = Color(0xFF0EA5E9),
    onSecondaryContainer = PrimeColors.TextPrimary,
    tertiary = PrimeColors.Secondary,
    onTertiary = PrimeColors.Background,
    background = PrimeColors.Background,
    onBackground = PrimeColors.TextPrimary,
    surface = PrimeColors.Surface,
    onSurface = PrimeColors.TextPrimary,
    surfaceVariant = PrimeColors.Card,
    onSurfaceVariant = PrimeColors.TextSecondary,
    outline = PrimeColors.Divider,
    outlineVariant = Color(0xFF64748B),
    error = PrimeColors.Error,
    onError = PrimeColors.TextPrimary,
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA)
)

val LightColors = lightColorScheme(
    primary = PrimeColors.Primary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E3A8A),
    secondary = PrimeColors.Secondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0C4A6E),
    tertiary = PrimeColors.Secondary,
    background = PrimeColors.LightBackground,
    onBackground = PrimeColors.LightTextPrimary,
    surface = PrimeColors.LightSurface,
    onSurface = PrimeColors.LightTextPrimary,
    surfaceVariant = PrimeColors.LightCard,
    onSurfaceVariant = PrimeColors.LightTextSecondary,
    outline = Color(0xFFCBD5E1),
    outlineVariant = Color(0xFFE2E8F0),
    error = PrimeColors.Error,
    onError = Color.White
)

@Composable
fun PrimeMediaTheme(
    settings: PrimeSettingsRepository,
    content: @Composable () -> Unit
) {
    val dark by settings.darkTheme.collectAsStateWithLifecycle(initialValue = true)
    MaterialTheme(
        colorScheme = if (dark) DarkColors else LightColors,
        typography = PrimeTypography,
        shapes = PrimeShapes,
        content = content
    )
}
