package com.example.messenger.ui.compose;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u000e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\f\u00a8\u0006\u0012"}, d2 = {"ChatThreadRow", "", "thread", "Lcom/example/messenger/data/entities/ChatThreadEntity;", "onClick", "Lkotlin/Function0;", "ChatsHubTabContent", "vm", "Lcom/example/messenger/ui/compose/ChatsHubViewModel;", "sessionUserId", "", "sessionUsername", "", "formatChatTime", "millis", "", "stableGroupKey", "threadId", "app_debug"})
public final class ChatsHubTabKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ChatsHubTabContent(@org.jetbrains.annotations.NotNull()
    com.example.messenger.ui.compose.ChatsHubViewModel vm, int sessionUserId, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionUsername) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ChatThreadRow(com.example.messenger.data.entities.ChatThreadEntity thread, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final java.lang.String formatChatTime(long millis) {
        return null;
    }
    
    /**
     * Стабильный числовой ключ для привязки сообщений Room к потоку группы/канала
     */
    public static final int stableGroupKey(@org.jetbrains.annotations.NotNull()
    java.lang.String threadId) {
        return 0;
    }
}