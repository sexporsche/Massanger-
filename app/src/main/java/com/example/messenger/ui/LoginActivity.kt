package com.example.messenger.ui

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.messenger.MessengerApplication
import com.example.messenger.R
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.SessionStore
import com.example.messenger.databinding.ActivityLoginBinding
import com.example.messenger.domain.model.UserRole
import com.example.messenger.network.SocketManager
import com.example.messenger.ui.compose.PrimeComposeActivity
import com.example.messenger.ui.compose.RegisterComposeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MessageRepository.init(applicationContext)

        binding.etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptLogin()
                true
            } else false
        }

        binding.btnLogin.setOnClickListener { attemptLogin() }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterComposeActivity::class.java))
        }
    }

    private fun attemptLogin() {
        val username = binding.etUsername.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString().orEmpty()
        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            return
        }

        binding.tvLoginError.isVisible = false
        binding.btnLogin.isEnabled = false
        binding.progressLogin.isVisible = true

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val host = getString(R.string.server_host)
                val port = resources.getInteger(R.integer.server_port)
                SocketManager.connect(applicationContext, host, port)
                SocketManager.sendLine("AUTH:$username:$password")
                val response = SocketManager.readLine()
                android.util.Log.d(TAG, "Login AUTH response: $response")
                if (response?.startsWith("AUTH_OK:") == true) {
                    val userId = response.substringAfter("AUTH_OK:").toIntOrNull() ?: 0
                    SessionStore.save(this@LoginActivity, userId, username, password)
                    val role = UserRole.fromServerUsername(username)
                    SessionStore.saveRole(this@LoginActivity, role.name)
                    val app = applicationContext as MessengerApplication
                    app.container.profileRepository.ensureAfterAuth(userId, username, role)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Авторизация успешна", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(this@LoginActivity, PrimeComposeActivity::class.java).apply {
                                putExtra("userId", userId)
                                putExtra("username", username)
                            }
                        )
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.tvLoginError.text = getString(R.string.error_auth_socket)
                        binding.tvLoginError.isVisible = true
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "Login error", e)
                withContext(Dispatchers.Main) {
                    binding.tvLoginError.text = e.message ?: "Ошибка подключения"
                    binding.tvLoginError.isVisible = true
                }
            } finally {
                SocketManager.disconnect()
                withContext(Dispatchers.Main) {
                    binding.btnLogin.isEnabled = true
                    binding.progressLogin.isVisible = false
                }
            }
        }
    }

    companion object {
        private const val TAG = "MessengerLogin"
    }
}
