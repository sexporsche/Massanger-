package com.example.messenger.di

import android.content.Context
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.repository.MockPrimeBackend
import com.example.messenger.data.repository.PrimeNewsRepositoryImpl
import com.example.messenger.data.repository.PrimeNotificationRepositoryImpl
import com.example.messenger.data.repository.PrimePollRepositoryImpl
import com.example.messenger.data.repository.PrimeProfileRepositoryImpl
import com.example.messenger.data.repository.PrimeSearchRepositoryImpl
import com.example.messenger.data.repository.PrimeSettingsRepositoryImpl
import com.example.messenger.domain.repository.PrimeNewsRepository
import com.example.messenger.domain.repository.PrimeNotificationRepository
import com.example.messenger.domain.repository.PrimePollRepository
import com.example.messenger.domain.repository.PrimeProfileRepository
import com.example.messenger.domain.repository.PrimeSearchRepository
import com.example.messenger.domain.repository.PrimeSettingsRepository

class AppContainer(context: Context) {
    private val appContext = context.applicationContext
    val database: AppDatabase = AppDatabase.getInstance(appContext)

    val mockBackend = MockPrimeBackend(database)

    val profileRepository: PrimeProfileRepository = PrimeProfileRepositoryImpl(database, mockBackend)
    val newsRepository: PrimeNewsRepository = PrimeNewsRepositoryImpl(database, mockBackend)
    val notificationRepository: PrimeNotificationRepository = PrimeNotificationRepositoryImpl(database, mockBackend)
    val pollRepository: PrimePollRepository = PrimePollRepositoryImpl(database, mockBackend)
    val settingsRepository: PrimeSettingsRepository = PrimeSettingsRepositoryImpl(appContext)
    val searchRepository: PrimeSearchRepository = PrimeSearchRepositoryImpl(database, mockBackend)
}
