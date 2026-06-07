package com.example.messenger.ui.compose.navigation;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\b\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/example/messenger/ui/compose/navigation/PrimeRoutes;", "", "()V", "ABOUT", "", "ADMIN_USERS", "ADMIN_USER_CREATE", "ADMIN_USER_EDIT", "CREATE_NEWS", "MAIN", "NEWS_DETAIL", "POLLS", "PROFILE", "SEARCH", "SETTINGS", "adminUserEdit", "userId", "", "app_debug"})
public final class PrimeRoutes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MAIN = "main";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SETTINGS = "settings";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ABOUT = "about";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String POLLS = "polls";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CREATE_NEWS = "create_news";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SEARCH = "search";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE = "profile/{userId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NEWS_DETAIL = "news/{newsId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ADMIN_USERS = "admin/users";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ADMIN_USER_CREATE = "admin/user_create";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ADMIN_USER_EDIT = "admin/user_edit/{userId}";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.messenger.ui.compose.navigation.PrimeRoutes INSTANCE = null;
    
    private PrimeRoutes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String adminUserEdit(int userId) {
        return null;
    }
}