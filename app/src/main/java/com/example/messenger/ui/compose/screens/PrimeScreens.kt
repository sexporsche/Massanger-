package com.example.messenger.ui.compose.screens

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.messenger.data.SessionStore
import com.example.messenger.domain.model.UserRole
import com.example.messenger.domain.model.canEditOwnAvatar
import com.example.messenger.domain.model.canEditOwnStatus
import com.example.messenger.domain.model.canNavigateToAdminEdit
import com.example.messenger.domain.model.canPublishNews
import com.example.messenger.domain.model.isAdmin
import com.example.messenger.ui.LoginActivity
import com.example.messenger.ui.compose.ChatsHubTabContent
import com.example.messenger.ui.compose.ChatsHubViewModel
import com.example.messenger.ui.compose.CreateNewsViewModel
import com.example.messenger.ui.compose.HomeViewModel
import com.example.messenger.ui.compose.NewsDetailViewModel
import com.example.messenger.ui.compose.NewsListViewModel
import com.example.messenger.ui.compose.NotificationsViewModel
import com.example.messenger.ui.compose.PollsViewModel
import com.example.messenger.ui.compose.ProfileViewModel
import com.example.messenger.ui.compose.SearchViewModel
import com.example.messenger.ui.compose.SettingsViewModel
import com.example.messenger.ui.compose.components.AvatarSize
import com.example.messenger.ui.compose.components.PrimeAvatar
import com.example.messenger.ui.compose.components.PrimeBottomNavigationBar
import com.example.messenger.ui.compose.components.PrimeCard
import com.example.messenger.ui.compose.components.PrimeDetailTopBar
import com.example.messenger.ui.compose.components.PrimeEmptyState
import com.example.messenger.ui.compose.components.PrimeNavItem
import com.example.messenger.ui.compose.components.PrimeSearchBar
import com.example.messenger.ui.compose.components.PrimeSettingsItem
import com.example.messenger.ui.compose.components.PrimeSettingsSection
import com.example.messenger.ui.compose.components.PrimeTopAppBar
import com.example.messenger.ui.compose.components.ProfileSkeleton
import com.example.messenger.ui.compose.navigation.PrimeRoutes
import com.example.messenger.ui.compose.theme.PrimeColors
import com.example.messenger.ui.compose.theme.PrimeSpacing
import kotlinx.coroutines.delay

private val bottomNavItems = listOf(
    PrimeNavItem("Главная", Icons.Outlined.Home, Icons.Filled.Home),
    PrimeNavItem("Новости", Icons.Outlined.Article, Icons.Filled.Article),
    PrimeNavItem("Чаты", Icons.Outlined.Chat, Icons.Filled.Chat),
    PrimeNavItem("Алерты", Icons.Outlined.Notifications, Icons.Filled.Notifications),
    PrimeNavItem("Ещё", Icons.Outlined.Person, Icons.Filled.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainShellScreen(
    tab: Int,
    onTabChange: (Int) -> Unit,
    sessionUserId: Int,
    sessionUsername: String,
    role: UserRole,
    onOpenLegacyContacts: () -> Unit,
    onNavigate: (String) -> Unit,
    homeVm: HomeViewModel = viewModel(),
    newsVm: NewsListViewModel = viewModel(),
    chatsVm: ChatsHubViewModel = viewModel(),
    notifVm: NotificationsViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            PrimeTopAppBar(
                title = "Прайм-Медиа",
                subtitle = "Корпоративный мессенджер",
                actions = {
                    IconButton(onClick = { onNavigate(PrimeRoutes.SEARCH) }) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                    IconButton(onClick = { onNavigate(PrimeRoutes.SETTINGS) }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Настройки")
                    }
                }
            )
        },
        floatingActionButton = {
            when (tab) {
                1 -> if (role.canPublishNews()) {
                    FloatingActionButton(
                        onClick = { onNavigate(PrimeRoutes.CREATE_NEWS) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Article, contentDescription = "Создать новость")
                    }
                }
                2 -> FloatingActionButton(
                    onClick = onOpenLegacyContacts,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Chat, contentDescription = "Новый чат")
                }
            }
        },
        bottomBar = {
            PrimeBottomNavigationBar(
                items = bottomNavItems,
                selectedIndex = tab,
                onItemSelected = onTabChange
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            Crossfade(targetState = tab, animationSpec = tween(250), label = "main_tab") { currentTab ->
                when (currentTab) {
                    0 -> HomeTabScreen(homeVm, sessionUserId, onNavigate)
                    1 -> NewsTabScreen(newsVm, onNavigate)
                    2 -> ChatsHubTabContent(chatsVm, sessionUserId, sessionUsername)
                    3 -> NotificationsTabScreen(notifVm)
                    4 -> MoreTabScreen(role, onNavigate, sessionUserId)
                }
            }
        }
    }
}

@Composable
private fun HomeTabScreen(vm: HomeViewModel, sessionUserId: Int, onNavigate: (String) -> Unit) {
    val deps by vm.departments.collectAsState()
    val threads by vm.threads.collectAsState()
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    LazyColumn(
        Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(PrimeSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(PrimeSpacing.md)
    ) {
        item {
            Text("Дашборд", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(
                "Обзор активности компании",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            PrimeCard(modifier = Modifier.fillMaxWidth(), onClick = { onNavigate("profile/$sessionUserId") }) {
                Row(Modifier.padding(PrimeSpacing.lg), verticalAlignment = Alignment.CenterVertically) {
                    PrimeAvatar(name = "Профиль", size = AvatarSize.Large)
                    Spacer(Modifier.width(PrimeSpacing.md))
                    Column {
                        Text("Мой профиль", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text("Просмотр и редактирование", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        item { Text("Отделы", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold) }
        items(deps) { d ->
            PrimeCard(modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(PrimeSpacing.lg), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(d.name.take(1), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(PrimeSpacing.md))
                    Column {
                        Text(d.name, style = MaterialTheme.typography.titleSmall)
                        Text("ID ${d.id}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        item {
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            Spacer(Modifier.height(PrimeSpacing.sm))
            Text("Активные каналы", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
        items(threads) { t ->
            PrimeCard(modifier = Modifier.fillMaxWidth(), onClick = { onNavigate("news/n-ceo-1") }) {
                Row(Modifier.padding(PrimeSpacing.lg), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    }
                    Spacer(Modifier.width(PrimeSpacing.md))
                    Column {
                        Text(t.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
                        Text(t.lastMessagePreview, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsTabScreen(vm: NewsListViewModel, onNavigate: (String) -> Unit) {
    val items by vm.items.collectAsState()
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    if (items.isEmpty()) {
        PrimeEmptyState(
            icon = Icons.Outlined.Article,
            title = "Нет новостей",
            subtitle = "Здесь будут публиковаться корпоративные новости"
        )
        return
    }
    LazyColumn(
        Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(PrimeSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(PrimeSpacing.md)
    ) {
        items(items, key = { it.id }) { n ->
            Card(
                Modifier.fillMaxWidth().clickable { onNavigate("news/${n.id}") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(Modifier.padding(PrimeSpacing.lg)) {
                    if (n.pinned) {
                        AssistChip(
                            onClick = {},
                            label = { Text("Закреплено", style = MaterialTheme.typography.labelSmall) },
                            leadingIcon = { Icon(Icons.Default.PushPin, contentDescription = null, Modifier.size(16.dp)) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                labelColor = MaterialTheme.colorScheme.primary
                            ),
                            border = null
                        )
                        Spacer(Modifier.height(PrimeSpacing.sm))
                    }
                    Text(n.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(PrimeSpacing.xs))
                    Text(n.body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 4, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(PrimeSpacing.md))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(16.dp), tint = PrimeColors.Error.copy(alpha = 0.8f))
                        Spacer(Modifier.width(4.dp))
                        Text("${n.likeCount}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationsTabScreen(vm: NotificationsViewModel) {
    val items by vm.items.collectAsState()
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = PrimeSpacing.lg, vertical = PrimeSpacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Уведомления", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            TextButton(onClick = { vm.markAll() }) { Text("Прочитать всё", color = MaterialTheme.colorScheme.primary) }
        }
        if (items.isEmpty()) {
            PrimeEmptyState(
                icon = Icons.Default.Notifications,
                title = "Нет уведомлений",
                subtitle = "Здесь будут появляться важные оповещения"
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = PrimeSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(PrimeSpacing.sm)
            ) {
                items(items) { n ->
                    PrimeCard(modifier = Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(PrimeSpacing.lg), verticalAlignment = Alignment.Top) {
                            Box(Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.primary))
                            Spacer(Modifier.width(PrimeSpacing.md))
                            Column {
                                Text(n.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.height(4.dp))
                                Text(n.body, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoreTabScreen(role: UserRole, onNavigate: (String) -> Unit, sessionUserId: Int) {
    LazyColumn(Modifier.fillMaxSize().padding(vertical = PrimeSpacing.sm)) {
        item {
            PrimeSettingsSection(title = "Аккаунт") {
                PrimeSettingsItem(Icons.Default.Person, "Профиль", "Личные данные и статус", onClick = { onNavigate("profile/$sessionUserId") })
                PrimeSettingsItem(Icons.Default.Article, "О компании", null, onClick = { onNavigate(PrimeRoutes.ABOUT) })
            }
        }
        if (role.canPublishNews() || role.isAdmin()) {
            item {
                PrimeSettingsSection(title = "Управление") {
                    if (role.canPublishNews()) {
                        PrimeSettingsItem(Icons.Default.Article, "Создать новость", "Публикация и объявления", onClick = { onNavigate(PrimeRoutes.CREATE_NEWS) })
                    }
                    if (role.isAdmin()) {
                        PrimeSettingsItem(Icons.Default.Person, "Пользователи", "Администрирование", onClick = { onNavigate(PrimeRoutes.ADMIN_USERS) })
                    }
                }
            }
        }
        item {
            PrimeSettingsSection(title = "Приложение") {
                PrimeSettingsItem(Icons.Outlined.Settings, "Настройки", "Тема, уведомления", onClick = { onNavigate(PrimeRoutes.SETTINGS) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val vm: SettingsViewModel = viewModel()
    val dark by vm.dark.collectAsState()
    val push by vm.push.collectAsState()
    val ctx = LocalContext.current
    Scaffold(topBar = { PrimeDetailTopBar(title = "Настройки", onBack = onBack) }) { pad ->
        LazyColumn(Modifier.padding(pad).fillMaxSize()) {
            item {
                PrimeSettingsSection(title = "Уведомления") {
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = PrimeSpacing.lg, vertical = PrimeSpacing.md),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Push-уведомления", style = MaterialTheme.typography.bodyLarge)
                            Text("Новые сообщения и алерты", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = push, onCheckedChange = { vm.setPush(it) })
                    }
                }
            }
            item {
                PrimeSettingsSection(title = "Внешний вид") {
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = PrimeSpacing.lg, vertical = PrimeSpacing.md),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Тёмная тема", style = MaterialTheme.typography.bodyLarge)
                            Text("Корпоративная тёмная палитра", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = dark, onCheckedChange = { vm.setDark(it) })
                    }
                }
            }
            item {
                PrimeSettingsSection(title = "О приложении") {
                    PrimeSettingsItem(Icons.Default.Article, "Прайм-Медиа", "Версия 1.0", onClick = { })
                }
            }
            item {
                Spacer(Modifier.height(PrimeSpacing.lg))
                TextButton(
                    onClick = {
                        vm.logout()
                        ctx.startActivity(Intent(ctx, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                        (ctx as? Activity)?.finishAffinity()
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = PrimeSpacing.lg)
                ) {
                    Text("Выйти из аккаунта", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(PrimeSpacing.xxxl))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(topBar = { PrimeDetailTopBar(title = "О компании", onBack = onBack) }) { pad ->
        Column(Modifier.padding(pad).padding(PrimeSpacing.lg)) {
            Box(Modifier.size(80.dp).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                Text("PM", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(PrimeSpacing.lg))
            Text("АО «Прайм-Медиа»", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(PrimeSpacing.sm))
            Text("Корпоративный мессенджер и новостная платформа для сотрудников компании.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(PrimeSpacing.md))
            Text("Версия 1.0", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollsScreen(onBack: () -> Unit) {
    val vm: PollsViewModel = viewModel()
    val polls by vm.polls.collectAsState()
    val opts by vm.firstPollOptions.collectAsState()
    Scaffold(topBar = { PrimeDetailTopBar(title = "Опросы", onBack = onBack) }) { pad ->
        LazyColumn(Modifier.padding(pad).padding(PrimeSpacing.lg), verticalArrangement = Arrangement.spacedBy(PrimeSpacing.sm)) {
            items(polls, key = { it.id }) { p ->
                Text(p.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = PrimeSpacing.sm))
            }
            items(opts, key = { it.id }) { o ->
                PrimeCard(modifier = Modifier.fillMaxWidth(), onClick = { vm.vote(o.id) }) {
                    Row(Modifier.fillMaxWidth().padding(PrimeSpacing.lg), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(o.label, style = MaterialTheme.typography.bodyLarge)
                        Text("${o.votes}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewsScreen(sessionUserId: Int, onBack: () -> Unit) {
    val ctx = LocalContext.current
    val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() } ?: UserRole.EMPLOYEE
    if (!role.canPublishNews()) {
        LaunchedEffect(Unit) { onBack() }
        return
    }
    val vm: CreateNewsViewModel = viewModel()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var pinned by remember { mutableStateOf(false) }
    var ann by remember { mutableStateOf(false) }
    Scaffold(topBar = { PrimeDetailTopBar(title = "Новая публикация", onBack = onBack) }) { pad ->
        Column(Modifier.padding(pad).padding(PrimeSpacing.lg), verticalArrangement = Arrangement.spacedBy(PrimeSpacing.md)) {
            OutlinedTextField(title, { title = it }, label = { Text("Заголовок") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            OutlinedTextField(body, { body = it }, label = { Text("Текст") }, modifier = Modifier.fillMaxWidth().height(160.dp), shape = RoundedCornerShape(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(PrimeSpacing.lg)) {
                Row(verticalAlignment = Alignment.CenterVertically) { Switch(pinned, { pinned = it }); Text("Закрепить", modifier = Modifier.padding(start = PrimeSpacing.sm)) }
                Row(verticalAlignment = Alignment.CenterVertically) { Switch(ann, { ann = it }); Text("Объявление", modifier = Modifier.padding(start = PrimeSpacing.sm)) }
            }
            Button(onClick = { vm.publish(title, body, pinned, ann, sessionUserId); onBack() }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Text("Опубликовать")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(onBack: () -> Unit, onPickNews: (String) -> Unit) {
    val vm: SearchViewModel = viewModel()
    var q by remember { mutableStateOf("") }
    val hits by remember(q) { vm.results(q) }.collectAsState()
    Scaffold(topBar = { PrimeDetailTopBar(title = "Поиск", onBack = onBack) }) { pad ->
        Column(Modifier.padding(pad).padding(PrimeSpacing.lg)) {
            PrimeSearchBar(query = q, onQueryChange = { q = it })
            Spacer(Modifier.height(PrimeSpacing.md))
            if (hits.isEmpty() && q.isNotBlank()) {
                PrimeEmptyState(icon = Icons.Outlined.Search, title = "Ничего не найдено", subtitle = "Попробуйте другой запрос")
            } else {
                LazyColumn {
                    items(hits) { h ->
                        PrimeCard(modifier = Modifier.fillMaxWidth().padding(vertical = PrimeSpacing.xs), onClick = { onPickNews(h.id) }) {
                            Column(Modifier.padding(PrimeSpacing.lg)) {
                                Text(h.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
                                Text(h.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(newsId: String, sessionUserId: Int, onBack: () -> Unit) {
    val app = LocalContext.current.applicationContext as Application
    val vm: NewsDetailViewModel = viewModel(factory = NewsDetailViewModel.factory(app, newsId))
    val article by vm.article.collectAsState()
    val comments by vm.comments.collectAsState()
    var draft by remember { mutableStateOf("") }
    var liked by remember { mutableStateOf(false) }
    Scaffold(topBar = { PrimeDetailTopBar(title = "Новость", onBack = onBack) }) { pad ->
        LazyColumn(Modifier.padding(pad).padding(PrimeSpacing.lg)) {
            item {
                AnimatedVisibility(visible = article != null) {
                    val a = article ?: return@AnimatedVisibility
                    Column {
                        Text(a.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(PrimeSpacing.md))
                        Text(a.body, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(PrimeSpacing.lg))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { liked = !liked; vm.like() }) {
                                Icon(if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Лайк", tint = if (liked) PrimeColors.Error else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Text("${a.likeCount}", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }
            item {
                HorizontalDivider(Modifier.padding(vertical = PrimeSpacing.md))
                Text("Комментарии", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(PrimeSpacing.sm))
            }
            items(comments) { c ->
                PrimeCard(modifier = Modifier.fillMaxWidth().padding(vertical = PrimeSpacing.xs)) {
                    Text(c.body, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(PrimeSpacing.md))
                }
            }
            item {
                Spacer(Modifier.height(PrimeSpacing.md))
                OutlinedTextField(draft, { draft = it }, label = { Text("Комментарий") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Button(onClick = { vm.postComment(draft, sessionUserId); draft = "" }, modifier = Modifier.fillMaxWidth().padding(top = PrimeSpacing.sm), shape = RoundedCornerShape(12.dp)) {
                    Text("Отправить")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userId: Int, sessionUserId: Int, onBack: () -> Unit, onNavigate: (String) -> Unit) {
    val ctx = LocalContext.current
    val app = ctx.applicationContext as Application
    val vm: ProfileViewModel = viewModel(factory = ProfileViewModel.factory(app, userId))
    val p by vm.profile.collectAsStateWithLifecycle()
    val departments by vm.departments.collectAsStateWithLifecycle()
    val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() } ?: UserRole.EMPLOYEE
    var loadError by remember(userId) { mutableStateOf(false) }
    LaunchedEffect(userId) {
        loadError = false
        delay(2000)
        if (vm.profile.value == null) loadError = true
    }
    LaunchedEffect(p) { if (p != null) loadError = false }
    val pickAvatar = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> uri?.let { vm.updatePhotoUri(it.toString()) } }
    var statusDraft by remember(p?.userId) { mutableStateOf(p?.statusText.orEmpty()) }
    LaunchedEffect(p?.userId, p?.statusText) { statusDraft = p?.statusText.orEmpty() }
    Scaffold(topBar = { PrimeDetailTopBar(title = "Профиль", onBack = onBack) }) { pad ->
        when {
            p == null && !loadError -> ProfileSkeleton(Modifier.padding(pad))
            p == null && loadError -> PrimeEmptyState(icon = Icons.Default.Person, title = "Профиль не найден", subtitle = "Данные недоступны в локальной базе")
            else -> {
                val profile = p!!
                LazyColumn(Modifier.padding(pad)) {
                    item {
                        Box(
                            Modifier.fillMaxWidth().height(180.dp).background(
                                androidx.compose.ui.graphics.Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer))
                            )
                        ) {
                            Column(Modifier.align(Alignment.BottomStart).padding(PrimeSpacing.lg)) {
                                PrimeAvatar(name = "${profile.firstName} ${profile.lastName}", photoUri = profile.photoUri, size = AvatarSize.XLarge, isOnline = userId == sessionUserId)
                            }
                        }
                    }
                    item {
                        Column(Modifier.padding(PrimeSpacing.lg)) {
                            if (profile.accountLocked) Text("Учётная запись заблокирована", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelMedium)
                            Text("${profile.firstName} ${profile.lastName}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text(profile.statusText.ifBlank { "Нет статуса" }, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(Modifier.height(PrimeSpacing.md))
                            AssistChip(onClick = {}, label = { Text(profile.role) }, colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)))
                            if (userId == sessionUserId && role.canEditOwnAvatar()) {
                                TextButton(onClick = { pickAvatar.launch("image/*") }) { Text("Сменить аватар") }
                            }
                        }
                    }
                    item {
                        PrimeSettingsSection(title = "Информация") {
                            PrimeSettingsItem(Icons.Default.Person, "Должность", profile.position, onClick = { })
                            profile.departmentId?.let { id -> departments.find { it.id == id }?.name }?.let { depName ->
                                PrimeSettingsItem(Icons.Default.Person, "Отдел", depName, onClick = { })
                            }
                            PrimeSettingsItem(Icons.Default.Person, "Email", profile.email, onClick = { })
                            PrimeSettingsItem(Icons.Default.Person, "Телефон", profile.phone, onClick = { })
                        }
                    }
                    item {
                        PrimeSettingsSection(title = "О себе") {
                            Text(profile.about.ifBlank { "—" }, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(PrimeSpacing.lg))
                        }
                    }
                    if (userId == sessionUserId && role.canEditOwnStatus()) {
                        item {
                            Column(Modifier.padding(PrimeSpacing.lg)) {
                                OutlinedTextField(statusDraft, { statusDraft = it }, label = { Text("Статус") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                                Button(onClick = { vm.updateStatusText(statusDraft) }, modifier = Modifier.padding(top = PrimeSpacing.sm)) { Text("Сохранить статус") }
                            }
                        }
                    }
                    if (role.canNavigateToAdminEdit(userId, sessionUserId)) {
                        item {
                            TextButton(onClick = { onNavigate(PrimeRoutes.adminUserEdit(userId)) }, modifier = Modifier.padding(PrimeSpacing.lg)) {
                                Text("Редактировать (админ)")
                            }
                        }
                    }
                }
            }
        }
    }
}
