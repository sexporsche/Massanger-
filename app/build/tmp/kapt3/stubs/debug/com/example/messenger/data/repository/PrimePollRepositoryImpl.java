package com.example.messenger.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\bH\u0016J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/example/messenger/data/repository/PrimePollRepositoryImpl;", "Lcom/example/messenger/domain/repository/PrimePollRepository;", "db", "Lcom/example/messenger/data/AppDatabase;", "mock", "Lcom/example/messenger/data/repository/MockPrimeBackend;", "(Lcom/example/messenger/data/AppDatabase;Lcom/example/messenger/data/repository/MockPrimeBackend;)V", "observeOptions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/messenger/data/entities/PollOptionEntity;", "pollId", "", "observePolls", "Lcom/example/messenger/data/entities/PollEntity;", "voteOption", "", "optionId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PrimePollRepositoryImpl implements com.example.messenger.domain.repository.PrimePollRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.repository.MockPrimeBackend mock = null;
    
    public PrimePollRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.AppDatabase db, @kotlin.Suppress(names = {"unused"})
    @org.jetbrains.annotations.NotNull()
    com.example.messenger.data.repository.MockPrimeBackend mock) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.PollEntity>> observePolls() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.PollOptionEntity>> observeOptions(@org.jetbrains.annotations.NotNull()
    java.lang.String pollId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object voteOption(@org.jetbrains.annotations.NotNull()
    java.lang.String optionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}