package com.example.messenger.ui.compose

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.messenger.data.entities.ChatThreadEntity
import com.example.messenger.ui.ChatActivity
import com.example.messenger.ui.compose.components.AvatarSize
import com.example.messenger.ui.compose.components.PrimeAvatar
import com.example.messenger.ui.compose.components.PrimeEmptyState
import com.example.messenger.ui.compose.components.PrimeSearchBar
import com.example.messenger.ui.compose.components.PrimeUnreadBadge
import com.example.messenger.ui.compose.theme.PrimeSpacing
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private enum class ChatInboxTab(val label: String, val predicate: (ChatThreadEntity) -> Boolean) {
    DIRECT("Личные", { it.type == "DIRECT" }),
    GROUP("Группы", { it.type == "GROUP" }),
    CHANNEL("Каналы", { it.type == "DEPARTMENT" });

    fun filter(list: List<ChatThreadEntity>) = list.filter(predicate)
}

@Composable
fun ChatsHubTabContent(
    vm: ChatsHubViewModel,
    sessionUserId: Int,
    sessionUsername: String
) {
    val ctx = LocalContext.current
    val threads by vm.threads.collectAsStateWithLifecycle()
    var tabIndex by rememberSaveable { mutableStateOf(0) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val tabs = ChatInboxTab.values()
    val filtered = remember(tabIndex, threads, searchQuery) {
        tabs[tabIndex].filter(threads).filter {
            searchQuery.isBlank() ||
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.lastMessagePreview.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(Modifier.fillMaxSize()) {
        PrimeSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            placeholder = "Поиск чатов",
            modifier = Modifier.padding(horizontal = PrimeSpacing.lg, vertical = PrimeSpacing.sm)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = PrimeSpacing.lg, vertical = PrimeSpacing.xs),
            horizontalArrangement = Arrangement.spacedBy(PrimeSpacing.sm)
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = index == tabIndex
                val bgColor by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    animationSpec = tween(200),
                    label = "tab_bg"
                )
                val textColor by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    animationSpec = tween(200),
                    label = "tab_text"
                )
                val interaction = remember(tab) { MutableInteractionSource() }
                Box(
                    Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor)
                        .clickable(interactionSource = interaction, indication = null) { tabIndex = index },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelLarge,
                        color = textColor,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        AnimatedContent(
            targetState = filtered.isEmpty(),
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(200)) },
            label = "chat_list"
        ) { isEmpty ->
            when {
                isEmpty && searchQuery.isNotBlank() -> PrimeEmptyState(
                    icon = Icons.Outlined.Forum,
                    title = "Ничего не найдено",
                    subtitle = "Попробуйте изменить запрос"
                )
                isEmpty -> PrimeEmptyState(
                    icon = Icons.Outlined.Forum,
                    title = "Нет чатов",
                    subtitle = "Начните переписку с коллегами"
                )
                else -> LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(PrimeSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(PrimeSpacing.sm)
                ) {
                    items(filtered, key = { it.id }) { thread ->
                        ChatThreadRow(
                            thread = thread,
                            onClick = {
                                when (thread.type) {
                                    "DIRECT" -> {
                                        val peer = thread.peerUserId ?: return@ChatThreadRow
                                        ctx.startActivity(
                                            Intent(ctx, ChatActivity::class.java).apply {
                                                putExtra("userId", peer)
                                                putExtra("username", thread.title.removePrefix("Личные: ").trim())
                                                putExtra("currentUserId", sessionUserId)
                                                putExtra("currentUsername", sessionUsername)
                                                putExtra("isOnline", false)
                                                putExtra("isGroupChat", false)
                                            }
                                        )
                                    }
                                    "GROUP", "DEPARTMENT" -> {
                                        val key = stableGroupKey(thread.id)
                                        ctx.startActivity(
                                            Intent(ctx, ChatActivity::class.java).apply {
                                                putExtra("isGroupChat", true)
                                                putExtra("groupChatKey", key)
                                                putExtra("groupTitle", thread.title)
                                                putExtra("currentUserId", sessionUserId)
                                                putExtra("currentUsername", sessionUsername)
                                                putExtra("threadId", thread.id)
                                            }
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatThreadRow(
    thread: ChatThreadEntity,
    onClick: () -> Unit
) {
    val displayName = thread.title.removePrefix("Личные: ").trim()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(PrimeSpacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimeAvatar(
                name = displayName,
                size = AvatarSize.Medium,
                isOnline = thread.type == "DIRECT"
            )
            Spacer(Modifier.width(PrimeSpacing.md))
            Column(Modifier.weight(1f)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Spacer(Modifier.width(PrimeSpacing.sm))
                    Text(
                        text = formatChatTime(thread.updatedAtMillis),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (thread.unread > 0) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = thread.lastMessagePreview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        fontWeight = if (thread.unread > 0) FontWeight.Medium else FontWeight.Normal
                    )
                    if (thread.unread > 0) {
                        Spacer(Modifier.width(PrimeSpacing.sm))
                        PrimeUnreadBadge(count = thread.unread)
                    }
                }
            }
        }
    }
}

private fun formatChatTime(millis: Long): String {
    if (millis <= 0L) return ""
    val now = Calendar.getInstance()
    val msg = Calendar.getInstance().apply { timeInMillis = millis }
    return when {
        now.get(Calendar.YEAR) == msg.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == msg.get(Calendar.DAY_OF_YEAR) ->
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(millis))
        now.get(Calendar.YEAR) == msg.get(Calendar.YEAR) &&
            now.get(Calendar.WEEK_OF_YEAR) == msg.get(Calendar.WEEK_OF_YEAR) ->
            SimpleDateFormat("EEE", Locale("ru")).format(Date(millis))
        now.get(Calendar.YEAR) == msg.get(Calendar.YEAR) ->
            SimpleDateFormat("d MMM", Locale("ru")).format(Date(millis))
        else -> SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(Date(millis))
    }
}

/** Стабильный числовой ключ для привязки сообщений Room к потоку группы/канала */
fun stableGroupKey(threadId: String): Int = threadId.hashCode()
