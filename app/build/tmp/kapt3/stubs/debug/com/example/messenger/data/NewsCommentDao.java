package com.example.messenger.data;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\'\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/example/messenger/data/NewsCommentDao;", "", "insert", "", "comment", "Lcom/example/messenger/data/entities/NewsCommentEntity;", "(Lcom/example/messenger/data/entities/NewsCommentEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeForNews", "Lkotlinx/coroutines/flow/Flow;", "", "newsId", "", "app_debug"})
@androidx.room.Dao()
public abstract interface NewsCommentDao {
    
    @androidx.room.Query(value = "SELECT * FROM news_comments WHERE news_id = :newsId ORDER BY created_at_millis ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.NewsCommentEntity>> observeForNews(@org.jetbrains.annotations.NotNull()
    java.lang.String newsId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.NewsCommentEntity comment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}