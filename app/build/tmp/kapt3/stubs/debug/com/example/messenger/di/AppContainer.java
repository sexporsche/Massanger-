package com.example.messenger.di;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 \u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&\u00a8\u0006\'"}, d2 = {"Lcom/example/messenger/di/AppContainer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "appContext", "kotlin.jvm.PlatformType", "database", "Lcom/example/messenger/data/AppDatabase;", "getDatabase", "()Lcom/example/messenger/data/AppDatabase;", "mockBackend", "Lcom/example/messenger/data/repository/MockPrimeBackend;", "getMockBackend", "()Lcom/example/messenger/data/repository/MockPrimeBackend;", "newsRepository", "Lcom/example/messenger/domain/repository/PrimeNewsRepository;", "getNewsRepository", "()Lcom/example/messenger/domain/repository/PrimeNewsRepository;", "notificationRepository", "Lcom/example/messenger/domain/repository/PrimeNotificationRepository;", "getNotificationRepository", "()Lcom/example/messenger/domain/repository/PrimeNotificationRepository;", "pollRepository", "Lcom/example/messenger/domain/repository/PrimePollRepository;", "getPollRepository", "()Lcom/example/messenger/domain/repository/PrimePollRepository;", "profileRepository", "Lcom/example/messenger/domain/repository/PrimeProfileRepository;", "getProfileRepository", "()Lcom/example/messenger/domain/repository/PrimeProfileRepository;", "searchRepository", "Lcom/example/messenger/domain/repository/PrimeSearchRepository;", "getSearchRepository", "()Lcom/example/messenger/domain/repository/PrimeSearchRepository;", "settingsRepository", "Lcom/example/messenger/domain/repository/PrimeSettingsRepository;", "getSettingsRepository", "()Lcom/example/messenger/domain/repository/PrimeSettingsRepository;", "app_debug"})
public final class AppContainer {
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.repository.MockPrimeBackend mockBackend = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.domain.repository.PrimeProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.domain.repository.PrimeNewsRepository newsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.domain.repository.PrimeNotificationRepository notificationRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.domain.repository.PrimePollRepository pollRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.domain.repository.PrimeSettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.domain.repository.PrimeSearchRepository searchRepository = null;
    
    public AppContainer(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.data.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.data.repository.MockPrimeBackend getMockBackend() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.domain.repository.PrimeProfileRepository getProfileRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.domain.repository.PrimeNewsRepository getNewsRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.domain.repository.PrimeNotificationRepository getNotificationRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.domain.repository.PrimePollRepository getPollRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.domain.repository.PrimeSettingsRepository getSettingsRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.messenger.domain.repository.PrimeSearchRepository getSearchRepository() {
        return null;
    }
}