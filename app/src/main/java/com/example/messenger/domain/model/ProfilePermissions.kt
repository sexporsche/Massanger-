package com.example.messenger.domain.model

fun UserRole.canOpenAdminPanel(): Boolean = isAdmin()

fun UserRole.canEditForeignProfile(): Boolean = isAdmin()

fun UserRole.canEditOwnCorporateFields(): Boolean = isAdmin()

fun UserRole.canEditOwnAvatar(): Boolean = true

fun UserRole.canEditOwnStatus(): Boolean = true

fun UserRole.canNavigateToAdminEdit(targetUserId: Int, sessionUserId: Int): Boolean =
    isAdmin() && targetUserId != sessionUserId
