package com.example.messenger.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/messenger/data/repository/PrimeSearchRepositoryImpl;", "Lcom/example/messenger/domain/repository/PrimeSearchRepository;", "db", "Lcom/example/messenger/data/AppDatabase;", "mock", "Lcom/example/messenger/data/repository/MockPrimeBackend;", "(Lcom/example/messenger/data/AppDatabase;Lcom/example/messenger/data/repository/MockPrimeBackend;)V", "observeQueryResults", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/messenger/domain/repository/SearchHit;", "query", "", "app_debug"})
public final class PrimeSearchRepositoryImpl implements com.example.messenger.domain.repository.PrimeSearchRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.repository.MockPrimeBackend mock = null;
    
    public PrimeSearchRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.AppDatabase db, @kotlin.Suppress(names = {"unused"})
    @org.jetbrains.annotations.NotNull()
    com.example.messenger.data.repository.MockPrimeBackend mock) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.domain.repository.SearchHit>> observeQueryResults(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
}