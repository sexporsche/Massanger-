package com.example.messenger.ui.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.SessionStore
import com.example.messenger.ui.LoginActivity
import com.example.messenger.ui.compose.theme.DarkColors
import com.example.messenger.ui.compose.theme.PrimeColors
import com.example.messenger.ui.compose.theme.PrimeTypography
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MessageRepository.init(applicationContext)
        val hasCredentials = SessionStore.hasCredentials(this)
        val userId = SessionStore.userId(this)
        val username = SessionStore.username(this).orEmpty()
        setContent {
            MaterialTheme(colorScheme = DarkColors, typography = PrimeTypography) {
                SplashScreen(
                    onFinish = {
                        val next = if (hasCredentials) {
                            Intent(this@SplashActivity, PrimeComposeActivity::class.java).apply {
                                putExtra("userId", userId)
                                putExtra("username", username)
                            }
                        } else {
                            Intent(this@SplashActivity, LoginActivity::class.java)
                        }
                        startActivity(next)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
private fun SplashScreen(onFinish: () -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 800),
        label = "splash_fade"
    )

    LaunchedEffect(Unit) {
        delay(900)
        onFinish()
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(PrimeColors.Background, PrimeColors.Surface, PrimeColors.Background)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha)
        ) {
            Box(
                Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(PrimeColors.Primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "PM",
                    style = MaterialTheme.typography.headlineMedium,
                    color = PrimeColors.TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "Прайм-Медиа",
                style = MaterialTheme.typography.headlineMedium,
                color = PrimeColors.TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Корпоративный мессенджер",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimeColors.TextSecondary
            )
            Spacer(Modifier.height(32.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = PrimeColors.Secondary,
                strokeWidth = 2.dp
            )
        }
    }
}
