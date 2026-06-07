package com.example.messenger.domain.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u00102\u0006\u0010\f\u001a\u00020\rH&J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u0010H&\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/example/messenger/domain/repository/PrimeNewsRepository;", "", "addArticle", "", "article", "Lcom/example/messenger/data/entities/NewsArticleEntity;", "(Lcom/example/messenger/data/entities/NewsArticleEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addComment", "comment", "Lcom/example/messenger/data/entities/NewsCommentEntity;", "(Lcom/example/messenger/data/entities/NewsCommentEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "like", "newsId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeComments", "Lkotlinx/coroutines/flow/Flow;", "", "observeNews", "app_debug"})
public abstract interface PrimeNewsRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.NewsArticleEntity>> observeNews();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.NewsCommentEntity>> observeComments(@org.jetbrains.annotations.NotNull()
    java.lang.String newsId);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addArticle(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.NewsArticleEntity article, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object like(@org.jetbrains.annotations.NotNull()
    java.lang.String newsId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addComment(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.NewsCommentEntity comment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}