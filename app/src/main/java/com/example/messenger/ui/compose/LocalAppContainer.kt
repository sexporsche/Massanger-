package com.example.messenger.ui.compose

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.messenger.di.AppContainer

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("AppContainer not provided")
}
