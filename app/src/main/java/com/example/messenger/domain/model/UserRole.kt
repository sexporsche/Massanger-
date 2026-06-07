package com.example.messenger.domain.model

enum class UserRole {
    EMPLOYEE,
    MANAGER,
    LEADER,
    ADMIN;

    companion object {
        fun fromServerUsername(username: String): UserRole {
            val u = username.trim().lowercase()
            return when {
                u == "admin" || u.endsWith(".admin") -> ADMIN
                u.contains("manager") || u.contains("менеджер") -> MANAGER
                u.contains("ceo") || u.contains("director") || u.contains("руковод") || u == "boss" -> LEADER
                else -> EMPLOYEE
            }
        }
    }
}

fun UserRole.canPublishNews(): Boolean =
    this == UserRole.LEADER || this == UserRole.ADMIN || this == UserRole.MANAGER

fun UserRole.isAdmin(): Boolean = this == UserRole.ADMIN
