package com.example.messenger.ui.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.messenger.MessengerApplication
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.SessionStore
import com.example.messenger.domain.model.UserRole
import com.example.messenger.ui.compose.components.PrimeDetailTopBar
import com.example.messenger.ui.compose.theme.DarkColors
import com.example.messenger.ui.compose.theme.PrimeColors
import com.example.messenger.ui.compose.theme.PrimeSpacing
import com.example.messenger.ui.compose.theme.PrimeTypography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MessageRepository.init(applicationContext)
        setContent {
            MaterialTheme(colorScheme = DarkColors, typography = PrimeTypography) {
                RegisterScreen(onBack = { finish() }, onCreateAccount = { user, pass ->
                    lifecycleScope.launch {
                        val id = (1000..9999).random()
                        SessionStore.save(this@RegisterComposeActivity, id, user, pass)
                        SessionStore.saveRole(this@RegisterComposeActivity, UserRole.EMPLOYEE.name)
                        val app = applicationContext as MessengerApplication
                        withContext(Dispatchers.IO) {
                            app.container.profileRepository.ensureAfterAuth(id, user, UserRole.EMPLOYEE)
                        }
                        startActivity(
                            Intent(this@RegisterComposeActivity, PrimeComposeActivity::class.java).apply {
                                putExtra("userId", id)
                                putExtra("username", user)
                            }
                        )
                        finish()
                    }
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterScreen(onBack: () -> Unit, onCreateAccount: (String, String) -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            PrimeDetailTopBar(title = "Регистрация", onBack = onBack)
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(PrimeColors.Background, PrimeColors.Surface)))
                .padding(PrimeSpacing.lg)
        ) {
            Text(
                "Создание аккаунта",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = PrimeColors.TextPrimary
            )
            Text(
                "Корпоративный мессенджер Прайм-Медиа",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimeColors.TextSecondary
            )
            Spacer(Modifier.height(PrimeSpacing.xxl))
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimeColors.Primary,
                    unfocusedBorderColor = PrimeColors.Divider
                )
            )
            Spacer(Modifier.height(PrimeSpacing.md))
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Пароль") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimeColors.Primary,
                    unfocusedBorderColor = PrimeColors.Divider
                )
            )
            Spacer(Modifier.height(PrimeSpacing.xxl))
            Button(
                onClick = { onCreateAccount(user, pass) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimeColors.Primary)
            ) {
                Text("Создать аккаунт")
            }
        }
    }
}
