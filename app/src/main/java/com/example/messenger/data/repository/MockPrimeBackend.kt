package com.example.messenger.data.repository

import com.example.messenger.data.AppDatabase
import com.example.messenger.data.entities.ChatThreadEntity
import com.example.messenger.data.entities.DepartmentEntity
import com.example.messenger.data.entities.InboxNotificationEntity
import com.example.messenger.data.entities.MessageEntity
import com.example.messenger.data.entities.NewsArticleEntity
import com.example.messenger.data.entities.NewsCommentEntity
import com.example.messenger.data.entities.PollEntity
import com.example.messenger.data.entities.PollOptionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Детерминированные мок-данные АО «Прайм-Медиа» + backend-ready точки расширения.
 */
class MockPrimeBackend(private val db: AppDatabase) {

    private val mutex = Mutex()

    suspend fun ensureSeed() = mutex.withLock {
        withContext(Dispatchers.IO) {
            if (db.departmentDao().count() > 0 && db.newsArticleDao().count() > 0) return@withContext
            android.util.Log.d(TAG, "Seeding Prime mock dataset…")

            val departments = listOf(
                DepartmentEntity(1, "Редакция"),
                DepartmentEntity(2, "IT"),
                DepartmentEntity(3, "Руководство")
            )
            db.departmentDao().insertAll(departments)

            val now = System.currentTimeMillis()
            val news = listOf(
                NewsArticleEntity(
                    id = "n-ceo-1",
                    title = "Стратегия Прайм-Медиа на 2026 год",
                    body = "Коллеги, фокус на мультимедиа, корпоративные коммуникации и сквозную аналитику.",
                    imageUrl = null,
                    authorUserId = 1,
                    departmentId = 3,
                    pinned = true,
                    createdAtMillis = now - 86_400_000L,
                    likeCount = 42,
                    announcement = true
                ),
                NewsArticleEntity(
                    id = "n-it-1",
                    title = "Обновление корпоративного мессенджера",
                    body = "Новая версия с ролями сотрудник / руководитель, лентой новостей и опросами.",
                    imageUrl = null,
                    authorUserId = 2,
                    departmentId = 2,
                    pinned = false,
                    createdAtMillis = now - 43_200_000L,
                    likeCount = 18,
                    announcement = false
                )
            )
            db.newsArticleDao().upsertAll(news)

            val comments = listOf(
                NewsCommentEntity(
                    id = "c-1",
                    newsId = "n-ceo-1",
                    authorUserId = 2,
                    body = "Принято, готовим дорожную карту по IT.",
                    createdAtMillis = now - 80_000_000L
                )
            )
            comments.forEach { db.newsCommentDao().insert(it) }

            val pollId = "p-1"
            db.pollDao().replacePollWithOptions(
                PollEntity(
                    id = pollId,
                    title = "Где провести корпоратив?",
                    createdByUserId = 1,
                    createdAtMillis = now - 10_000_000L,
                    closed = false
                ),
                listOf(
                    PollOptionEntity(id = "po-1", pollId = pollId, label = "Москва", votes = 12),
                    PollOptionEntity(id = "po-2", pollId = pollId, label = "Сочи", votes = 9),
                    PollOptionEntity(id = "po-3", pollId = pollId, label = "Онлайн", votes = 5)
                )
            )

            val notifications = listOf(
                InboxNotificationEntity(
                    id = "in-1",
                    title = "Новый документ на согласовании",
                    body = "Договор с контрагентом «Прайм-Медиа Продакшн».",
                    createdAtMillis = now - 3_600_000L,
                    read = false
                ),
                InboxNotificationEntity(
                    id = "in-2",
                    title = "Встреча совета директоров",
                    body = "Завтра 10:00, зал 401.",
                    createdAtMillis = now - 7_200_000L,
                    read = true
                )
            )
            db.inboxNotificationDao().insertAll(notifications)

            val threads = listOf(
                ChatThreadEntity(
                    id = "t-dept-1",
                    title = "Канал: Редакция",
                    type = "DEPARTMENT",
                    peerUserId = null,
                    departmentId = 1,
                    lastMessagePreview = "Сверстали выпуск №12",
                    updatedAtMillis = now - 600_000L,
                    unread = 2
                ),
                ChatThreadEntity(
                    id = "t-group-1",
                    title = "Проект «Эфир»",
                    type = "GROUP",
                    peerUserId = null,
                    departmentId = null,
                    lastMessagePreview = "График смен обновлён",
                    updatedAtMillis = now - 1_200_000L,
                    unread = 0
                ),
                ChatThreadEntity(
                    id = "t-dm-2",
                    title = "Личные: user2",
                    type = "DIRECT",
                    peerUserId = 2,
                    departmentId = null,
                    lastMessagePreview = "Спасибо, материал принят",
                    updatedAtMillis = now - 3_000_000L,
                    unread = 1
                )
            )
            db.chatThreadDao().upsertAll(threads)

            val groupKey = "t-group-1".hashCode()
            val groupMsgs = listOf(
                MessageEntity(
                    id = 0L,
                    senderId = 1,
                    recipientId = null,
                    groupId = groupKey,
                    text = "Коллеги, график смен обновлён.",
                    filePath = null,
                    timestamp = now - 900_000L,
                    isRead = false
                ),
                MessageEntity(
                    id = 0L,
                    senderId = 2,
                    recipientId = null,
                    groupId = groupKey,
                    text = "Принято, выкладываю в общий диск.",
                    filePath = null,
                    timestamp = now - 800_000L,
                    isRead = false
                )
            )
            db.messageDao().insertAll(groupMsgs)
        }
    }

    fun newId(prefix: String): String = "$prefix-${UUID.randomUUID().toString().substring(0, 8)}"

    companion object {
        private const val TAG = "PrimeMockBackend"
    }
}
