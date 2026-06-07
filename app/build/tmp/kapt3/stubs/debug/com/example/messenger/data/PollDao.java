package com.example.messenger.data;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\r2\u0006\u0010\u000e\u001a\u00020\u000fH\'J\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00050\rH\'J\'\u0010\u0011\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0097@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Lcom/example/messenger/data/PollDao;", "", "insertOptions", "", "options", "", "Lcom/example/messenger/data/entities/PollOptionEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPoll", "poll", "Lcom/example/messenger/data/entities/PollEntity;", "(Lcom/example/messenger/data/entities/PollEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeOptions", "Lkotlinx/coroutines/flow/Flow;", "pollId", "", "observePolls", "replacePollWithOptions", "(Lcom/example/messenger/data/entities/PollEntity;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "voteOption", "optionId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface PollDao {
    
    @androidx.room.Query(value = "SELECT * FROM polls ORDER BY created_at_millis DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.PollEntity>> observePolls();
    
    @androidx.room.Query(value = "SELECT * FROM poll_options WHERE poll_id = :pollId ORDER BY votes DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.PollOptionEntity>> observeOptions(@org.jetbrains.annotations.NotNull()
    java.lang.String pollId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPoll(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.PollEntity poll, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOptions(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.messenger.data.entities.PollOptionEntity> options, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object replacePollWithOptions(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.PollEntity poll, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.messenger.data.entities.PollOptionEntity> options, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE poll_options SET votes = votes + 1 WHERE id = :optionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object voteOption(@org.jetbrains.annotations.NotNull()
    java.lang.String optionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object replacePollWithOptions(@org.jetbrains.annotations.NotNull()
        com.example.messenger.data.PollDao $this, @org.jetbrains.annotations.NotNull()
        com.example.messenger.data.entities.PollEntity poll, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.messenger.data.entities.PollOptionEntity> options, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}