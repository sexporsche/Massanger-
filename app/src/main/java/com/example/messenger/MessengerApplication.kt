package com.example.messenger

import android.app.Application
import com.example.messenger.di.AppContainer

class MessengerApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        com.example.messenger.data.MessageRepository.init(this)
        container = AppContainer(this)
    }
}
