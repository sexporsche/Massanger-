package com.example.messenger.data;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\nH\'J\u0018\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0019\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"Lcom/example/messenger/data/UserProfileDao;", "", "getProfile", "Lcom/example/messenger/data/entities/UserProfileEntity;", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "maxUserId", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllProfiles", "Lkotlinx/coroutines/flow/Flow;", "", "observeProfile", "upsert", "", "profile", "(Lcom/example/messenger/data/entities/UserProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface UserProfileDao {
    
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE userId = :id LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.messenger.data.entities.UserProfileEntity> observeProfile(int id);
    
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE userId = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProfile(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.messenger.data.entities.UserProfileEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_profiles ORDER BY last_name COLLATE NOCASE ASC, first_name COLLATE NOCASE ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.messenger.data.entities.UserProfileEntity>> observeAllProfiles();
    
    @androidx.room.Query(value = "SELECT MAX(userId) FROM user_profiles")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object maxUserId(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsert(@org.jetbrains.annotations.NotNull()
    com.example.messenger.data.entities.UserProfileEntity profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}