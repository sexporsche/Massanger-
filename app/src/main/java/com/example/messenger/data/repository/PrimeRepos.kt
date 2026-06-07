package com.example.messenger.data.repository

import android.content.Context
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.entities.InboxNotificationEntity
import com.example.messenger.data.entities.NewsArticleEntity
import com.example.messenger.data.entities.NewsCommentEntity
import com.example.messenger.data.entities.PollEntity
import com.example.messenger.data.entities.PollOptionEntity
import com.example.messenger.data.entities.UserProfileEntity
import com.example.messenger.domain.model.UserRole
import com.example.messenger.domain.repository.PrimeNewsRepository
import com.example.messenger.domain.repository.PrimeNotificationRepository
import com.example.messenger.domain.repository.PrimePollRepository
import com.example.messenger.domain.repository.PrimeProfileRepository
import com.example.messenger.domain.repository.PrimeSearchRepository
import com.example.messenger.domain.repository.PrimeSettingsRepository
import com.example.messenger.domain.repository.SearchHit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PrimeProfileRepositoryImpl(
    private val db: AppDatabase,
    @Suppress("unused") private val mock: MockPrimeBackend
) : PrimeProfileRepository {

    override fun observeProfile(userId: Int): Flow<UserProfileEntity?> =
        db.userProfileDao().observeProfile(userId)

    override fun observeAllProfiles(): Flow<List<UserProfileEntity>> =
        db.userProfileDao().observeAllProfiles()

    override suspend fun nextUserIdForCreate(): Int =
        (db.userProfileDao().maxUserId() ?: 0) + 1

    override suspend fun ensureAfterAuth(userId: Int, username: String, role: UserRole) {
        val existing = db.userProfileDao().getProfile(userId)
        val now = System.currentTimeMillis()
        if (existing != null) return
        val (position, departmentId) = when (role) {
            UserRole.ADMIN -> "Администратор" to 2
            UserRole.MANAGER -> "Менеджер" to 1
            UserRole.LEADER -> "Руководитель" to 3
            UserRole.EMPLOYEE -> "Сотрудник" to 1
        }
        val profile = UserProfileEntity(
            userId = userId,
            firstName = username,
            lastName = "—",
            middleName = "",
            photoUri = null,
            position = position,
            departmentId = departmentId,
            email = "$username@prime-media.ru",
            phone = "+7 (900) 000-00-00",
            birthDateMillis = null,
            statusText = "В эфире",
            about = "АО «Прайм-Медиа»",
            registeredAtMillis = now,
            role = role.name,
            isOnline = true,
            accountLocked = false
        )
        db.userProfileDao().upsert(profile)
    }

    override suspend fun upsert(profile: UserProfileEntity) {
        db.userProfileDao().upsert(profile)
    }
}

class PrimeNewsRepositoryImpl(
    private val db: AppDatabase,
    @Suppress("unused") private val mock: MockPrimeBackend
) : PrimeNewsRepository {

    override fun observeNews(): Flow<List<NewsArticleEntity>> = db.newsArticleDao().observeArticles()

    override fun observeComments(newsId: String): Flow<List<NewsCommentEntity>> =
        db.newsCommentDao().observeForNews(newsId)

    override suspend fun addArticle(article: NewsArticleEntity) {
        db.newsArticleDao().upsert(article)
    }

    override suspend fun like(newsId: String) {
        db.newsArticleDao().incrementLike(newsId)
    }

    override suspend fun addComment(comment: NewsCommentEntity) {
        db.newsCommentDao().insert(comment)
    }
}

class PrimeNotificationRepositoryImpl(
    private val db: AppDatabase,
    @Suppress("unused") private val mock: MockPrimeBackend
) : PrimeNotificationRepository {

    override fun observeInbox(): Flow<List<InboxNotificationEntity>> =
        db.inboxNotificationDao().observeAll()

    override suspend fun markRead(id: String) {
        db.inboxNotificationDao().markRead(id)
    }

    override suspend fun markAllRead() {
        db.inboxNotificationDao().markAllRead()
    }
}

class PrimePollRepositoryImpl(
    private val db: AppDatabase,
    @Suppress("unused") private val mock: MockPrimeBackend
) : PrimePollRepository {

    override fun observePolls(): Flow<List<PollEntity>> = db.pollDao().observePolls()

    override fun observeOptions(pollId: String): Flow<List<PollOptionEntity>> =
        db.pollDao().observeOptions(pollId)

    override suspend fun voteOption(optionId: String) {
        db.pollDao().voteOption(optionId)
    }
}

class PrimeSettingsRepositoryImpl(context: Context) : PrimeSettingsRepository {

    private val prefs = context.applicationContext.getSharedPreferences("prime_settings", Context.MODE_PRIVATE)

    private val _dark = MutableStateFlow(prefs.getBoolean("dark", true))
    private val _push = MutableStateFlow(prefs.getBoolean("push", true))
    private val _lang = MutableStateFlow(prefs.getString("lang", "ru") ?: "ru")

    override val darkTheme: Flow<Boolean> = _dark
    override val pushEnabled: Flow<Boolean> = _push
    override val languageCode: Flow<String> = _lang

    override suspend fun setDarkTheme(enabled: Boolean) {
        prefs.edit().putBoolean("dark", enabled).apply()
        _dark.value = enabled
    }

    override suspend fun setPushEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("push", enabled).apply()
        _push.value = enabled
    }

    override suspend fun setLanguageCode(code: String) {
        prefs.edit().putString("lang", code).apply()
        _lang.value = code
    }
}

class PrimeSearchRepositoryImpl(
    private val db: AppDatabase,
    @Suppress("unused") private val mock: MockPrimeBackend
) : PrimeSearchRepository {

    override fun observeQueryResults(query: String): Flow<List<SearchHit>> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return flowOf(emptyList())
        return db.newsArticleDao().observeArticles().map { articles ->
            articles.filter {
                it.title.lowercase().contains(q) || it.body.lowercase().contains(q)
            }.map {
                SearchHit(it.id, it.title, it.body.take(80), "news/${it.id}")
            }
        }
    }
}
