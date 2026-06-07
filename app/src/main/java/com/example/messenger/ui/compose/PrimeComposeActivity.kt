package com.example.messenger.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.messenger.MessengerApplication
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.SessionStore
import com.example.messenger.ui.compose.navigation.PrimeNavHost
import com.example.messenger.ui.compose.theme.PrimeMediaTheme

class PrimeComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MessageRepository.init(applicationContext)
        val userId = intent.getIntExtra("userId", SessionStore.userId(this))
        val username = intent.getStringExtra("username") ?: SessionStore.username(this).orEmpty()
        val app = application as MessengerApplication

        setContent {
            LaunchedEffect(Unit) { app.container.mockBackend.ensureSeed() }
            val navController = rememberNavController()
            PrimeMediaTheme(settings = app.container.settingsRepository) {
                Surface {
                    CompositionLocalProvider(
                        LocalAppContainer provides app.container
                    ) {
                        PrimeNavHost(
                            sessionUserId = userId,
                            sessionUsername = username,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
