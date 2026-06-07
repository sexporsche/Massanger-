package com.example.messenger.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.messenger.data.entities.AttachmentMetaEntity
import com.example.messenger.data.entities.ChatThreadEntity
import com.example.messenger.data.entities.DepartmentEntity
import com.example.messenger.data.entities.InboxNotificationEntity
import com.example.messenger.data.entities.MessageEntity
import com.example.messenger.data.entities.NewsArticleEntity
import com.example.messenger.data.entities.NewsCommentEntity
import com.example.messenger.data.entities.PollEntity
import com.example.messenger.data.entities.PollOptionEntity
import com.example.messenger.data.entities.UserEntity
import com.example.messenger.data.entities.UserProfileEntity

@Database(
    entities = [
        MessageEntity::class,
        UserEntity::class,
        DepartmentEntity::class,
        UserProfileEntity::class,
        NewsArticleEntity::class,
        NewsCommentEntity::class,
        PollEntity::class,
        PollOptionEntity::class,
        InboxNotificationEntity::class,
        ChatThreadEntity::class,
        AttachmentMetaEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun newsArticleDao(): NewsArticleDao
    abstract fun newsCommentDao(): NewsCommentDao
    abstract fun pollDao(): PollDao
    abstract fun inboxNotificationDao(): InboxNotificationDao
    abstract fun chatThreadDao(): ChatThreadDao
    abstract fun attachmentDao(): AttachmentDao

    companion object {
        private const val TAG = "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "messenger.db"
                )
                    .addMigrations(PrimeMigrations.MIGRATION_1_2, PrimeMigrations.MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d(TAG, "Room onCreate v2 schema")
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
