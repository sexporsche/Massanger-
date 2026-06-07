package com.example.messenger.data;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&\u00a8\u0006\u0018"}, d2 = {"Lcom/example/messenger/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "attachmentDao", "Lcom/example/messenger/data/AttachmentDao;", "chatThreadDao", "Lcom/example/messenger/data/ChatThreadDao;", "departmentDao", "Lcom/example/messenger/data/DepartmentDao;", "inboxNotificationDao", "Lcom/example/messenger/data/InboxNotificationDao;", "messageDao", "Lcom/example/messenger/data/MessageDao;", "newsArticleDao", "Lcom/example/messenger/data/NewsArticleDao;", "newsCommentDao", "Lcom/example/messenger/data/NewsCommentDao;", "pollDao", "Lcom/example/messenger/data/PollDao;", "userDao", "Lcom/example/messenger/data/UserDao;", "userProfileDao", "Lcom/example/messenger/data/UserProfileDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.example.messenger.data.entities.MessageEntity.class, com.example.messenger.data.entities.UserEntity.class, com.example.messenger.data.entities.DepartmentEntity.class, com.example.messenger.data.entities.UserProfileEntity.class, com.example.messenger.data.entities.NewsArticleEntity.class, com.example.messenger.data.entities.NewsCommentEntity.class, com.example.messenger.data.entities.PollEntity.class, com.example.messenger.data.entities.PollOptionEntity.class, com.example.messenger.data.entities.InboxNotificationEntity.class, com.example.messenger.data.entities.ChatThreadEntity.class, com.example.messenger.data.entities.AttachmentMetaEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AppDatabase";
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.example.messenger.data.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.messenger.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.MessageDao messageDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.DepartmentDao departmentDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.UserProfileDao userProfileDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.NewsArticleDao newsArticleDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.NewsCommentDao newsCommentDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.PollDao pollDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.InboxNotificationDao inboxNotificationDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.ChatThreadDao chatThreadDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.messenger.data.AttachmentDao attachmentDao();
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/messenger/data/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/example/messenger/data/AppDatabase;", "TAG", "", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.messenger.data.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}