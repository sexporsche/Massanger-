package com.example.messenger.ui.compose.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.messenger.data.SessionStore
import com.example.messenger.domain.model.UserRole
import com.example.messenger.ui.ContactsActivity
import com.example.messenger.ui.compose.AdminCreateUserRoute
import com.example.messenger.ui.compose.AdminEditUserRoute
import com.example.messenger.ui.compose.AdminUsersRoute
import com.example.messenger.ui.compose.screens.AboutScreen
import com.example.messenger.ui.compose.screens.CreateNewsScreen
import com.example.messenger.ui.compose.screens.MainShellScreen
import com.example.messenger.ui.compose.screens.NewsDetailScreen
import com.example.messenger.ui.compose.screens.PollsScreen
import com.example.messenger.ui.compose.screens.ProfileScreen
import com.example.messenger.ui.compose.screens.SearchScreen
import com.example.messenger.ui.compose.screens.SettingsScreen
import androidx.lifecycle.SavedStateHandle

object PrimeRoutes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
    const val POLLS = "polls"
    const val CREATE_NEWS = "create_news"
    const val SEARCH = "search"
    const val PROFILE = "profile/{userId}"
    const val NEWS_DETAIL = "news/{newsId}"
    const val ADMIN_USERS = "admin/users"
    const val ADMIN_USER_CREATE = "admin/user_create"
    const val ADMIN_USER_EDIT = "admin/user_edit/{userId}"

    fun adminUserEdit(userId: Int) = "admin/user_edit/$userId"
}

private const val KEY_MAIN_TAB = "prime_main_bottom_tab"

@Composable
fun PrimeNavHost(
    sessionUserId: Int,
    sessionUsername: String,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = PrimeRoutes.MAIN) {
        composable(PrimeRoutes.MAIN) { entry ->
            val ctx = LocalContext.current
            val role = SessionStore.role(ctx)?.let { runCatching { UserRole.valueOf(it) }.getOrNull() } ?: UserRole.EMPLOYEE
            var tab by rememberSaveable { mutableStateOf(entry.savedStateHandle.get<Int>(KEY_MAIN_TAB) ?: 0) }
            SideEffect { entry.savedStateHandle[KEY_MAIN_TAB] = tab }
            MainShellScreen(
                tab = tab,
                onTabChange = { tab = it },
                sessionUserId = sessionUserId,
                sessionUsername = sessionUsername,
                role = role,
                onOpenLegacyContacts = {
                    ctx.startActivity(
                        Intent(ctx, ContactsActivity::class.java).apply {
                            putExtra("userId", sessionUserId)
                            putExtra("username", sessionUsername)
                        }
                    )
                },
                onNavigate = { route ->
                    navController.navigate(route) { launchSingleTop = true }
                }
            )
        }
        composable(PrimeRoutes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(PrimeRoutes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable(PrimeRoutes.POLLS) {
            PollsScreen(onBack = { navController.popBackStack() })
        }
        composable(PrimeRoutes.CREATE_NEWS) {
            CreateNewsScreen(sessionUserId = sessionUserId, onBack = { navController.popBackStack() })
        }
        composable(PrimeRoutes.SEARCH) {
            SearchScreen(
                onBack = { navController.popBackStack() },
                onPickNews = { id ->
                    navController.navigate("news/$id") { launchSingleTop = true }
                }
            )
        }
        composable(
            route = PrimeRoutes.NEWS_DETAIL,
            arguments = listOf(navArgument("newsId") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("newsId") ?: return@composable
            NewsDetailScreen(newsId = id, sessionUserId = sessionUserId, onBack = { navController.popBackStack() })
        }
        composable(
            route = PrimeRoutes.PROFILE,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { entry ->
            val uid = entry.arguments?.getInt("userId") ?: return@composable
            ProfileScreen(
                userId = uid,
                sessionUserId = sessionUserId,
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) { launchSingleTop = true } }
            )
        }
        composable(PrimeRoutes.ADMIN_USERS) {
            AdminUsersRoute(navController = navController)
        }
        composable(PrimeRoutes.ADMIN_USER_CREATE) {
            AdminCreateUserRoute(navController = navController)
        }
        composable(
            route = PrimeRoutes.ADMIN_USER_EDIT,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { entry ->
            val uid = entry.arguments?.getInt("userId") ?: return@composable
            AdminEditUserRoute(userId = uid, navController = navController)
        }
    }
}
