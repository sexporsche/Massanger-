package com.example.messenger.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.messenger.data.SessionStore
import com.example.messenger.data.entities.UserProfileEntity
import com.example.messenger.domain.model.UserRole
import com.example.messenger.domain.model.isAdmin
import com.example.messenger.ui.compose.components.PrimeCard
import com.example.messenger.ui.compose.components.PrimeDetailTopBar
import com.example.messenger.ui.compose.components.PrimeSearchBar
import com.example.messenger.ui.compose.navigation.PrimeRoutes
import com.example.messenger.ui.compose.theme.PrimeSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersRoute(navController: NavHostController) {
    val ctx = LocalContext.current
    val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() }
    LaunchedEffect(role) {
        if (role?.isAdmin() != true) navController.popBackStack()
    }
    if (role?.isAdmin() != true) return

    val vm: AdminUsersViewModel = viewModel()
    val users by vm.users.collectAsStateWithLifecycle()
    var q by remember { mutableStateOf("") }

    LaunchedEffect(q) { vm.setQuery(q) }

    Scaffold(
        topBar = {
            PrimeDetailTopBar(
                title = "Пользователи",
                onBack = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(PrimeRoutes.ADMIN_USER_CREATE) }) {
                Icon(Icons.Default.Add, contentDescription = "Создать")
            }
        }
    ) { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            PrimeSearchBar(
                query = q,
                onQueryChange = { q = it },
                modifier = Modifier.padding(PrimeSpacing.lg)
            )
            LazyColumn(
                contentPadding = PaddingValues(horizontal = PrimeSpacing.lg, vertical = PrimeSpacing.sm),
                verticalArrangement = Arrangement.spacedBy(PrimeSpacing.sm)
            ) {
                items(users, key = { it.userId }) { u ->
                    PrimeCard(modifier = Modifier.fillMaxWidth(), onClick = {
                        navController.navigate(PrimeRoutes.adminUserEdit(u.userId))
                    }) {
                        ListItem(
                            headlineContent = { Text("${u.lastName} ${u.firstName}") },
                            supportingContent = {
                                Text(
                                    listOf(u.position, u.role, u.email, if (u.accountLocked) "заблокирован" else "")
                                        .filter { it.isNotBlank() }
                                        .joinToString(" · ")
                                )
                            },
                            modifier = Modifier.padding(PrimeSpacing.md)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEditUserRoute(userId: Int, navController: NavHostController) {
    val ctx = LocalContext.current
    val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() }
    LaunchedEffect(role) {
        if (role?.isAdmin() != true) navController.popBackStack()
    }
    if (role?.isAdmin() != true) return

    val app = ctx.applicationContext as android.app.Application
    val vm: AdminEditUserViewModel = viewModel(factory = AdminEditUserViewModel.factory(app, userId))
    val profile by vm.profile.collectAsStateWithLifecycle()
    val departments by vm.departments.collectAsStateWithLifecycle()

    var first by remember(profile) { mutableStateOf(profile?.firstName.orEmpty()) }
    var last by remember(profile) { mutableStateOf(profile?.lastName.orEmpty()) }
    var position by remember(profile) { mutableStateOf(profile?.position.orEmpty()) }
    var email by remember(profile) { mutableStateOf(profile?.email.orEmpty()) }
    var phone by remember(profile) { mutableStateOf(profile?.phone.orEmpty()) }
    var about by remember(profile) { mutableStateOf(profile?.about.orEmpty()) }
    var status by remember(profile) { mutableStateOf(profile?.statusText.orEmpty()) }
    var roleStr by remember(profile) { mutableStateOf(profile?.role ?: UserRole.EMPLOYEE.name) }
    var deptId by remember(profile) { mutableStateOf(profile?.departmentId) }
    var locked by remember(profile) { mutableStateOf(profile?.accountLocked ?: false) }
    var photo by remember(profile) { mutableStateOf(profile?.photoUri.orEmpty()) }

    LaunchedEffect(profile) {
        profile?.let { p ->
            first = p.firstName
            last = p.lastName
            position = p.position
            email = p.email
            phone = p.phone
            about = p.about
            status = p.statusText
            roleStr = p.role
            deptId = p.departmentId
            locked = p.accountLocked
            photo = p.photoUri.orEmpty()
        }
    }

    Scaffold(
        topBar = {
            PrimeDetailTopBar(
                title = "Редактирование #$userId",
                onBack = { navController.popBackStack() }
            )
        }
    ) { pad ->
        if (profile == null) {
            Text("Загрузка…", Modifier.padding(pad).padding(24.dp))
            return@Scaffold
        }
        val p0 = profile!!
        LazyColumn(
            Modifier
                .padding(pad)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                OutlinedTextField(first, { first = it }, label = { Text("Имя") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(last, { last = it }, label = { Text("Фамилия") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(position, { position = it }, label = { Text("Должность") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(phone, { phone = it }, label = { Text("Телефон") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(about, { about = it }, label = { Text("О себе") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(status, { status = it }, label = { Text("Статус") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(photo, { photo = it }, label = { Text("Avatar URI") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                Text("Роль", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    UserRole.values().forEach { r ->
                        AssistChip(
                            onClick = { roleStr = r.name },
                            label = { Text(if (roleStr == r.name) "✓ ${r.name}" else r.name) }
                        )
                    }
                }
            }
            item {
                Text("Отдел", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = { deptId = null }, label = { Text("—") })
                    departments.forEach { d ->
                        AssistChip(
                            onClick = { deptId = d.id },
                            label = { Text(if (deptId == d.id) "✓ ${d.name}" else d.name) }
                        )
                    }
                }
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Заблокировать вход")
                    Switch(checked = locked, onCheckedChange = { locked = it })
                }
            }
            item {
                TextButton(
                    onClick = {
                        val parsedRole = runCatching { UserRole.valueOf(roleStr) }.getOrNull() ?: UserRole.EMPLOYEE
                        vm.save(
                            p0.copy(
                                firstName = first,
                                lastName = last,
                                position = position,
                                email = email,
                                phone = phone,
                                about = about,
                                statusText = status,
                                photoUri = photo.ifBlank { null },
                                role = parsedRole.name,
                                departmentId = deptId,
                                accountLocked = locked
                            )
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Сохранить") }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCreateUserRoute(navController: NavHostController) {
    val ctx = LocalContext.current
    val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() }
    LaunchedEffect(role) {
        if (role?.isAdmin() != true) navController.popBackStack()
    }
    if (role?.isAdmin() != true) return

    val vm: AdminCreateUserViewModel = viewModel()
    val departments by vm.departments.collectAsStateWithLifecycle()

    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("Сотрудник") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+7") }
    var about by remember { mutableStateOf("АО «Прайм-Медиа»") }

    Scaffold(
        topBar = {
            PrimeDetailTopBar(
                title = "Новый пользователь",
                onBack = { navController.popBackStack() }
            )
        }
    ) { pad ->
        LazyColumn(
            Modifier
                .padding(pad)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                OutlinedTextField(first, { first = it }, label = { Text("Имя") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(last, { last = it }, label = { Text("Фамилия") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(position, { position = it }, label = { Text("Должность") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(phone, { phone = it }, label = { Text("Телефон") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(about, { about = it }, label = { Text("О себе") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                TextButton(
                    onClick = {
                        val newUser = UserProfileEntity(
                            userId = 0,
                            firstName = first,
                            lastName = last,
                            position = position,
                            email = email,
                            phone = phone,
                            about = about,
                            statusText = "",
                            role = UserRole.EMPLOYEE.name,
                            accountLocked = false,
                            departmentId = null,
                            registeredAtMillis = System.currentTimeMillis()
                        )
                        vm.create(newUser) {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = first.isNotBlank() && last.isNotBlank() && email.isNotBlank()
                ) { Text("Создать") }
            }
        }
    }
}
