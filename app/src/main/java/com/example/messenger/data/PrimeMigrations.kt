package com.example.messenger.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object PrimeMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `departments` (
                `id` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `user_profiles` (
                `userId` INTEGER NOT NULL,
                `first_name` TEXT NOT NULL,
                `last_name` TEXT NOT NULL,
                `middle_name` TEXT NOT NULL,
                `photo_uri` TEXT,
                `position` TEXT NOT NULL,
                `department_id` INTEGER,
                `email` TEXT NOT NULL,
                `phone` TEXT NOT NULL,
                `birth_date_millis` INTEGER,
                `status_text` TEXT NOT NULL,
                `about` TEXT NOT NULL,
                `registered_at_millis` INTEGER NOT NULL,
                `role` TEXT NOT NULL,
                `is_online` INTEGER NOT NULL,
                PRIMARY KEY(`userId`)
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `news_articles` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `body` TEXT NOT NULL,
                `image_url` TEXT,
                `author_user_id` INTEGER NOT NULL,
                `department_id` INTEGER,
                `pinned` INTEGER NOT NULL,
                `created_at_millis` INTEGER NOT NULL,
                `like_count` INTEGER NOT NULL,
                `announcement` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_news_articles_created_at_millis` ON `news_articles` (`created_at_millis`)"
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `news_comments` (
                `id` TEXT NOT NULL,
                `news_id` TEXT NOT NULL,
                `author_user_id` INTEGER NOT NULL,
                `body` TEXT NOT NULL,
                `created_at_millis` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `polls` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `created_by_user_id` INTEGER NOT NULL,
                `created_at_millis` INTEGER NOT NULL,
                `closed` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `poll_options` (
                `id` TEXT NOT NULL,
                `poll_id` TEXT NOT NULL,
                `label` TEXT NOT NULL,
                `votes` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_poll_options_poll_id` ON `poll_options` (`poll_id`)"
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `notifications_inbox` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `body` TEXT NOT NULL,
                `created_at_millis` INTEGER NOT NULL,
                `read` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_notifications_inbox_created_at_millis` ON `notifications_inbox` (`created_at_millis`)"
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `chat_threads` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `type` TEXT NOT NULL,
                `peer_user_id` INTEGER,
                `department_id` INTEGER,
                `last_message_preview` TEXT NOT NULL,
                `updated_at_millis` INTEGER NOT NULL,
                `unread` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_chat_threads_updated_at_millis` ON `chat_threads` (`updated_at_millis`)"
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `attachments_meta` (
                `id` TEXT NOT NULL,
                `owner_type` TEXT NOT NULL,
                `owner_id` TEXT NOT NULL,
                `uri` TEXT NOT NULL,
                `mime` TEXT NOT NULL,
                `size_bytes` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_attachments_meta_owner_type_owner_id` ON `attachments_meta` (`owner_type`, `owner_id`)"
            )
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE user_profiles ADD COLUMN account_locked INTEGER NOT NULL DEFAULT 0"
            )
        }
    }
}
