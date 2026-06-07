package com.example.messenger.domain.repository

import com.example.messenger.data.entities.NewsArticleEntity
import com.example.messenger.data.entities.NewsCommentEntity
import com.example.messenger.data.entities.UserProfileEntity
import com.example.messenger.domain.model.UserRole
import kotlinx.coroutines.flow.Flow

interface PrimeProfileRepository {
    fun observeProfile(userId: Int): Flow<UserProfileEntity?>
    fun observeAllProfiles(): Flow<List<UserProfileEntity>>
    suspend fun ensureAfterAuth(userId: Int, username: String, role: UserRole)
    suspend fun upsert(profile: UserProfileEntity)
    suspend fun nextUserIdForCreate(): Int
}

interface PrimeNewsRepository {
    fun observeNews(): Flow<List<NewsArticleEntity>>
    fun observeComments(newsId: String): Flow<List<NewsCommentEntity>>
    suspend fun addArticle(article: NewsArticleEntity)
    suspend fun like(newsId: String)
    suspend fun addComment(comment: NewsCommentEntity)
}

interface PrimeNotificationRepository {
    fun observeInbox(): Flow<List<com.example.messenger.data.entities.InboxNotificationEntity>>
    suspend fun markRead(id: String)
    suspend fun markAllRead()
}

interface PrimePollRepository {
    fun observePolls(): Flow<List<com.example.messenger.data.entities.PollEntity>>
    fun observeOptions(pollId: String): Flow<List<com.example.messenger.data.entities.PollOptionEntity>>
    suspend fun voteOption(optionId: String)
}

interface PrimeSettingsRepository {
    val darkTheme: Flow<Boolean>
    val pushEnabled: Flow<Boolean>
    val languageCode: Flow<String>
    suspend fun setDarkTheme(enabled: Boolean)
    suspend fun setPushEnabled(enabled: Boolean)
    suspend fun setLanguageCode(code: String)
}

data class SearchHit(
    val id: String,
    val title: String,
    val subtitle: String,
    val route: String
)

interface PrimeSearchRepository {
    fun observeQueryResults(query: String): Flow<List<SearchHit>>
}
