package com.example.messenger.domain.model;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\u001a\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0002\u00a8\u0006\u000b"}, d2 = {"canEditForeignProfile", "", "Lcom/example/messenger/domain/model/UserRole;", "canEditOwnAvatar", "canEditOwnCorporateFields", "canEditOwnStatus", "canNavigateToAdminEdit", "targetUserId", "", "sessionUserId", "canOpenAdminPanel", "app_debug"})
public final class ProfilePermissionsKt {
    
    public static final boolean canOpenAdminPanel(@org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole $this$canOpenAdminPanel) {
        return false;
    }
    
    public static final boolean canEditForeignProfile(@org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole $this$canEditForeignProfile) {
        return false;
    }
    
    public static final boolean canEditOwnCorporateFields(@org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole $this$canEditOwnCorporateFields) {
        return false;
    }
    
    public static final boolean canEditOwnAvatar(@org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole $this$canEditOwnAvatar) {
        return false;
    }
    
    public static final boolean canEditOwnStatus(@org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole $this$canEditOwnStatus) {
        return false;
    }
    
    public static final boolean canNavigateToAdminEdit(@org.jetbrains.annotations.NotNull()
    com.example.messenger.domain.model.UserRole $this$canNavigateToAdminEdit, int targetUserId, int sessionUserId) {
        return false;
    }
}