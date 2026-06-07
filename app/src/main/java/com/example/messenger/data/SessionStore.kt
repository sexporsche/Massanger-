package com.example.messenger.data

import android.content.Context

/**
 * Minimal session for re-authenticating TLS sockets (server expects AUTH per connection).
 * Replace with secure token storage in production.
 */
object SessionStore {
    private const val PREFS = "messenger_session"

    private fun prefs(ctx: Context) =
        ctx.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun save(context: Context, userId: Int, username: String, password: String) {
        prefs(context).edit()
            .putInt("user_id", userId)
            .putString("username", username)
            .putString("password", password)
            .apply()
    }

    fun saveRole(context: Context, role: String) {
        prefs(context).edit().putString("user_role", role).apply()
    }

    fun role(context: Context): String? = prefs(context).getString("user_role", null)

    fun userId(context: Context): Int = prefs(context).getInt("user_id", 0)

    fun username(context: Context): String? = prefs(context).getString("username", null)

    fun password(context: Context): String? = prefs(context).getString("password", null)

    fun clear(context: Context) {
        prefs(context).edit().clear().apply()
    }

    fun hasCredentials(context: Context): Boolean =
        !username(context).isNullOrBlank() && !password(context).isNullOrBlank()
}
