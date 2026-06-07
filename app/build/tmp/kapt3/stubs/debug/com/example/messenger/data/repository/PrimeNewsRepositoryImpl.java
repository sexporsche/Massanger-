package com.example.messenger.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0019\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00160\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00160\u0015H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Lcom/example/messenger/data/repository/PrimeNewsRepositoryImpl;", "Lcom/example/messenger/domain/repository/PrimeNewsRepository;", "db", "Lcom/example/messenger/data/AppDatabase;", "mock", "Lcom/example/messenger/data/repository/MockPrimeBackend;", "(Lcom/example/messenger/data/AppDatabase;Lcom/example/messenger/data/repository/MockPrimeBackend;)V", "addArticle", "", "article", "Lcom/example/messenger/data/entities/NewsArticleEntity;", "(Lcom/example/messenger/data/entities/NewsArticleEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addComment", "comment", "Lcom/example/messenger/data/entities/NewsCommentEntity;", "(Lcom/example/messenger/data/entities/NewsCommentEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "like", "newsId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeComments", "Lkotlinx/coroutines/flow/Flow;", "", "observeNews", "app_debug"})
public final class PrimeNewsRepositoryImpl implements com.example.messenger.domain.repository.PrimeNewsRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.messenger.data.repository.MockPrimeBackend mock = null;
    
    public PrimeNewsRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.AppDatabase db, @kotlin.Suppress(names = {"unused"})
    @org.jetbrains.annotations.NotNull()
    com.example.messenger.data.repository.MockPrimeBackend mock) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.NewsArticleEntity>> observeNews() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.NewsCommentEntity>> observeComments(@org.jetbrains.annotations.NotNull()
    java.lang.String newsId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addArticle(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.NewsArticleEntity article, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object like(@org.jetbrains.annotations.NotNull()
    java.lang.String newsId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addComment(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.NewsCommentEntity comment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}