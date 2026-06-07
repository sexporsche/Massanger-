package com.example.messenger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.messenger.data.entities.AttachmentMetaEntity
import com.example.messenger.data.entities.ChatThreadEntity
import com.example.messenger.data.entities.DepartmentEntity
import com.example.messenger.data.entities.InboxNotificationEntity
import com.example.messenger.data.entities.NewsArticleEntity
import com.example.messenger.data.entities.NewsCommentEntity
import com.example.messenger.data.entities.PollEntity
import com.example.messenger.data.entities.PollOptionEntity
import com.example.messenger.data.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {
    @Query("SELECT COUNT(*) FROM departments")
    suspend fun count(): Int

    @Query("SELECT * FROM departments ORDER BY name ASC")
    fun observeDepartments(): Flow<List<DepartmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(departments: List<DepartmentEntity>)
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE userId = :id LIMIT 1")
    fun observeProfile(id: Int): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profiles WHERE userId = :id LIMIT 1")
    suspend fun getProfile(id: Int): UserProfileEntity?

    @Query("SELECT * FROM user_profiles ORDER BY last_name COLLATE NOCASE ASC, first_name COLLATE NOCASE ASC")
    fun observeAllProfiles(): Flow<List<UserProfileEntity>>

    @Query("SELECT MAX(userId) FROM user_profiles")
    suspend fun maxUserId(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: UserProfileEntity)
}

@Dao
interface NewsArticleDao {
    @Query("SELECT COUNT(*) FROM news_articles")
    suspend fun count(): Int

    @Query("SELECT * FROM news_articles ORDER BY pinned DESC, created_at_millis DESC")
    fun observeArticles(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE id = :id LIMIT 1")
    fun observeArticle(id: String): Flow<NewsArticleEntity?>

    @Query("SELECT * FROM news_articles WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): NewsArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<NewsArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: NewsArticleEntity)

    @Query("UPDATE news_articles SET like_count = like_count + 1 WHERE id = :id")
    suspend fun incrementLike(id: String)
}

@Dao
interface NewsCommentDao {
    @Query("SELECT * FROM news_comments WHERE news_id = :newsId ORDER BY created_at_millis ASC")
    fun observeForNews(newsId: String): Flow<List<NewsCommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: NewsCommentEntity)
}

@Dao
interface PollDao {
    @Query("SELECT * FROM polls ORDER BY created_at_millis DESC")
    fun observePolls(): Flow<List<PollEntity>>

    @Query("SELECT * FROM poll_options WHERE poll_id = :pollId ORDER BY votes DESC")
    fun observeOptions(pollId: String): Flow<List<PollOptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoll(poll: PollEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<PollOptionEntity>)

    @Transaction
    suspend fun replacePollWithOptions(poll: PollEntity, options: List<PollOptionEntity>) {
        insertPoll(poll)
        insertOptions(options)
    }

    @Query("UPDATE poll_options SET votes = votes + 1 WHERE id = :optionId")
    suspend fun voteOption(optionId: String)
}

@Dao
interface InboxNotificationDao {
    @Query("SELECT * FROM notifications_inbox ORDER BY created_at_millis DESC")
    fun observeAll(): Flow<List<InboxNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<InboxNotificationEntity>)

    @Query("UPDATE notifications_inbox SET `read` = 1 WHERE id = :id")
    suspend fun markRead(id: String)

    @Query("UPDATE notifications_inbox SET `read` = 1")
    suspend fun markAllRead()
}

@Dao
interface ChatThreadDao {
    @Query("SELECT * FROM chat_threads ORDER BY updated_at_millis DESC")
    fun observeThreads(): Flow<List<ChatThreadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(threads: List<ChatThreadEntity>)
}

@Dao
interface AttachmentDao {
    @Query("SELECT * FROM attachments_meta WHERE owner_type = :type AND owner_id = :ownerId")
    fun observeForOwner(type: String, ownerId: String): Flow<List<AttachmentMetaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AttachmentMetaEntity)
}
