package com.example.messenger.ui.compose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.messenger.MessengerApplication
import com.example.messenger.data.SessionStore
import com.example.messenger.data.entities.ChatThreadEntity
import com.example.messenger.data.entities.DepartmentEntity
import com.example.messenger.data.entities.InboxNotificationEntity
import com.example.messenger.data.entities.NewsArticleEntity
import com.example.messenger.data.entities.NewsCommentEntity
import com.example.messenger.data.entities.PollOptionEntity
import com.example.messenger.data.entities.UserProfileEntity
import com.example.messenger.domain.model.UserRole
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val departments: StateFlow<List<DepartmentEntity>> =
        c.database.departmentDao().observeDepartments()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val threads: StateFlow<List<ChatThreadEntity>> =
        c.database.chatThreadDao().observeThreads()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

class NewsListViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val items: StateFlow<List<NewsArticleEntity>> =
        c.newsRepository.observeNews()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

class NewsDetailViewModel(app: Application, private val newsId: String) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val article: StateFlow<NewsArticleEntity?> =
        c.database.newsArticleDao().observeArticle(newsId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val comments: StateFlow<List<NewsCommentEntity>> =
        c.newsRepository.observeComments(newsId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun like() {
        viewModelScope.launch { c.newsRepository.like(newsId) }
    }

    fun postComment(text: String, authorId: Int) {
        viewModelScope.launch {
            val id = c.mockBackend.newId("c")
            c.newsRepository.addComment(
                NewsCommentEntity(
                    id = id,
                    newsId = newsId,
                    authorUserId = authorId,
                    body = text,
                    createdAtMillis = System.currentTimeMillis()
                )
            )
        }
    }

    companion object {
        fun factory(app: Application, newsId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                NewsDetailViewModel(app, newsId) as T
        }
    }
}

class ChatsHubViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val threads: StateFlow<List<ChatThreadEntity>> =
        c.database.chatThreadDao().observeThreads()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

class NotificationsViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val items: StateFlow<List<InboxNotificationEntity>> =
        c.notificationRepository.observeInbox()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun markAll() {
        viewModelScope.launch { c.notificationRepository.markAllRead() }
    }
}

class PollsViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    private val pollsFlow = c.pollRepository.observePolls()
    val polls = pollsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val firstPollOptions: StateFlow<List<PollOptionEntity>> =
        pollsFlow.flatMapLatest { list ->
            val id = list.firstOrNull()?.id
            if (id == null) flowOf(emptyList()) else c.pollRepository.observeOptions(id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun vote(optionId: String) {
        viewModelScope.launch { c.pollRepository.voteOption(optionId) }
    }
}

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val dark = c.settingsRepository.darkTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)
    val push = c.settingsRepository.pushEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)
    val lang = c.settingsRepository.languageCode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "ru")

    fun setDark(v: Boolean) {
        viewModelScope.launch { c.settingsRepository.setDarkTheme(v) }
    }

    fun setPush(v: Boolean) {
        viewModelScope.launch { c.settingsRepository.setPushEnabled(v) }
    }

    fun setLang(code: String) {
        viewModelScope.launch { c.settingsRepository.setLanguageCode(code) }
    }

    fun logout() {
        SessionStore.clear(getApplication())
    }
}

class ProfileViewModel(app: Application, private val userId: Int) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val profile: StateFlow<UserProfileEntity?> =
        c.profileRepository.observeProfile(userId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val departments: StateFlow<List<DepartmentEntity>> =
        c.database.departmentDao().observeDepartments()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            val ctx = getApplication<Application>()
            if (userId != SessionStore.userId(ctx)) return@launch
            val un = SessionStore.username(ctx).orEmpty()
            if (un.isBlank()) return@launch
            val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() }
                ?: UserRole.EMPLOYEE
            c.profileRepository.ensureAfterAuth(userId, un, role)
        }
    }

    fun save(p: UserProfileEntity) {
        viewModelScope.launch { c.profileRepository.upsert(p) }
    }

    fun updatePhotoUri(uri: String?) {
        viewModelScope.launch {
            val cur = c.database.userProfileDao().getProfile(userId) ?: return@launch
            c.profileRepository.upsert(cur.copy(photoUri = uri))
        }
    }

    fun updateStatusText(status: String) {
        viewModelScope.launch {
            val cur = c.database.userProfileDao().getProfile(userId) ?: return@launch
            c.profileRepository.upsert(cur.copy(statusText = status))
        }
    }

    companion object {
        fun factory(app: Application, userId: Int): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = ProfileViewModel(app, userId) as T
        }
    }
}

class CreateNewsViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container

    fun publish(title: String, body: String, pinned: Boolean, announcement: Boolean, authorId: Int) {
        viewModelScope.launch {
            val id = c.mockBackend.newId("n")
            c.newsRepository.addArticle(
                NewsArticleEntity(
                    id = id,
                    title = title,
                    body = body,
                    imageUrl = null,
                    authorUserId = authorId,
                    departmentId = null,
                    pinned = pinned,
                    createdAtMillis = System.currentTimeMillis(),
                    likeCount = 0,
                    announcement = announcement
                )
            )
        }
    }
}

class SearchViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    fun results(query: String) = c.searchRepository.observeQueryResults(query)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList<com.example.messenger.domain.repository.SearchHit>())
}
