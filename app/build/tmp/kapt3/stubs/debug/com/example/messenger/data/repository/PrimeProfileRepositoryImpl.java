package com.example.messenger.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J)\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0011\u0010\u0010\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013H\u0016J\u0018\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u00132\u0006\u0010\t\u001a\u00020\nH\u0016J\u0019\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0015H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/example/messenger/data/repository/PrimeProfileRepositoryImpl;", "Lcom/example/messenger/domain/repository/PrimeProfileRepository;", "db", "Lcom/example/messenger/data/AppDatabase;", "mock", "Lcom/example/messenger/data/repository/MockPrimeBackend;", "(Lcom/example/messenger/data/AppDatabase;Lcom/example/messenger/data/repository/MockPrimeBackend;)V", "ensureAfterAuth", "", "userId", "", "username", "", "role", "Lcom/example/messenger/domain/model/UserRole;", "(ILjava/lang/String;Lcom/example/messenger/domain/model/UserRole;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "nextUserIdForCreate", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllProfiles", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/messenger/data/entities/UserProfileEntity;", "observeProfile", "upsert", "profile", "(Lcom/example/messenger/data/entities/UserProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PrimeProfileRepositoryImpl implements com.example.messenger.domain.repository.PrimeProfileRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.repository.MockPrimeBackend mock = null;
    
    public PrimeProfileRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.AppDatabase db, @kotlin.Suppress(names = {"unused"})
    @org.jetbrains.annotations.NotNull()
    com.example.messenger.data.repository.MockPrimeBackend mock) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.example.messenger.data.entities.UserProfileEntity> observeProfile(int userId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.UserProfileEntity>> observeAllProfiles() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object nextUserIdForCreate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object ensureAfterAuth(int userId, @org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole role, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object upsert(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.UserProfileEntity profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}