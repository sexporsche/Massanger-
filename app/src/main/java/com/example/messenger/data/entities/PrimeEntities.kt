package com.example.messenger.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "middle_name") val middleName: String = "",
    @ColumnInfo(name = "photo_uri") val photoUri: String? = null,
    val position: String,
    @ColumnInfo(name = "department_id") val departmentId: Int?,
    val email: String,
    val phone: String,
    @ColumnInfo(name = "birth_date_millis") val birthDateMillis: Long? = null,
    @ColumnInfo(name = "status_text") val statusText: String,
    val about: String,
    @ColumnInfo(name = "registered_at_millis") val registeredAtMillis: Long,
    val role: String,
    @ColumnInfo(name = "is_online") val isOnline: Boolean = false,
    @ColumnInfo(name = "account_locked") val accountLocked: Boolean = false
)

@Entity(
    tableName = "news_articles",
    indices = [Index(value = ["created_at_millis"])]
)
data class NewsArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "author_user_id") val authorUserId: Int,
    @ColumnInfo(name = "department_id") val departmentId: Int?,
    val pinned: Boolean,
    @ColumnInfo(name = "created_at_millis") val createdAtMillis: Long,
    @ColumnInfo(name = "like_count") val likeCount: Int,
    val announcement: Boolean
)

@Entity(tableName = "news_comments")
data class NewsCommentEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "news_id") val newsId: String,
    @ColumnInfo(name = "author_user_id") val authorUserId: Int,
    val body: String,
    @ColumnInfo(name = "created_at_millis") val createdAtMillis: Long
)

@Entity(tableName = "polls")
data class PollEntity(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo(name = "created_by_user_id") val createdByUserId: Int,
    @ColumnInfo(name = "created_at_millis") val createdAtMillis: Long,
    val closed: Boolean
)

@Entity(
    tableName = "poll_options",
    indices = [Index(value = ["poll_id"])]
)
data class PollOptionEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "poll_id") val pollId: String,
    val label: String,
    val votes: Int
)

@Entity(
    tableName = "notifications_inbox",
    indices = [Index(value = ["created_at_millis"])]
)
data class InboxNotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    @ColumnInfo(name = "created_at_millis") val createdAtMillis: Long,
    val read: Boolean
)

@Entity(
    tableName = "chat_threads",
    indices = [Index(value = ["updated_at_millis"])]
)
data class ChatThreadEntity(
    @PrimaryKey val id: String,
    val title: String,
    val type: String,
    @ColumnInfo(name = "peer_user_id") val peerUserId: Int?,
    @ColumnInfo(name = "department_id") val departmentId: Int?,
    @ColumnInfo(name = "last_message_preview") val lastMessagePreview: String,
    @ColumnInfo(name = "updated_at_millis") val updatedAtMillis: Long,
    val unread: Int
)

@Entity(
    tableName = "attachments_meta",
    indices = [Index(value = ["owner_type", "owner_id"])]
)
data class AttachmentMetaEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "owner_type") val ownerType: String,
    @ColumnInfo(name = "owner_id") val ownerId: String,
    val uri: String,
    val mime: String,
    @ColumnInfo(name = "size_bytes") val sizeBytes: Long
)
