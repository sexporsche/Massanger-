package com.example.messenger.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile MessageDao _messageDao;

  private volatile UserDao _userDao;

  private volatile DepartmentDao _departmentDao;

  private volatile UserProfileDao _userProfileDao;

  private volatile NewsArticleDao _newsArticleDao;

  private volatile NewsCommentDao _newsCommentDao;

  private volatile PollDao _pollDao;

  private volatile InboxNotificationDao _inboxNotificationDao;

  private volatile ChatThreadDao _chatThreadDao;

  private volatile AttachmentDao _attachmentDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `messages` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sender_id` INTEGER NOT NULL, `recipient_id` INTEGER, `group_id` INTEGER, `message_text` TEXT, `file_path` TEXT, `timestamp` INTEGER NOT NULL, `is_read` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `password_hash` TEXT NOT NULL, `salt` TEXT NOT NULL, `isOnline` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `departments` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profiles` (`userId` INTEGER NOT NULL, `first_name` TEXT NOT NULL, `last_name` TEXT NOT NULL, `middle_name` TEXT NOT NULL, `photo_uri` TEXT, `position` TEXT NOT NULL, `department_id` INTEGER, `email` TEXT NOT NULL, `phone` TEXT NOT NULL, `birth_date_millis` INTEGER, `status_text` TEXT NOT NULL, `about` TEXT NOT NULL, `registered_at_millis` INTEGER NOT NULL, `role` TEXT NOT NULL, `is_online` INTEGER NOT NULL, `account_locked` INTEGER NOT NULL, PRIMARY KEY(`userId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `news_articles` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `body` TEXT NOT NULL, `image_url` TEXT, `author_user_id` INTEGER NOT NULL, `department_id` INTEGER, `pinned` INTEGER NOT NULL, `created_at_millis` INTEGER NOT NULL, `like_count` INTEGER NOT NULL, `announcement` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_news_articles_created_at_millis` ON `news_articles` (`created_at_millis`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `news_comments` (`id` TEXT NOT NULL, `news_id` TEXT NOT NULL, `author_user_id` INTEGER NOT NULL, `body` TEXT NOT NULL, `created_at_millis` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `polls` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `created_by_user_id` INTEGER NOT NULL, `created_at_millis` INTEGER NOT NULL, `closed` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `poll_options` (`id` TEXT NOT NULL, `poll_id` TEXT NOT NULL, `label` TEXT NOT NULL, `votes` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_poll_options_poll_id` ON `poll_options` (`poll_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notifications_inbox` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `body` TEXT NOT NULL, `created_at_millis` INTEGER NOT NULL, `read` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notifications_inbox_created_at_millis` ON `notifications_inbox` (`created_at_millis`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chat_threads` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `type` TEXT NOT NULL, `peer_user_id` INTEGER, `department_id` INTEGER, `last_message_preview` TEXT NOT NULL, `updated_at_millis` INTEGER NOT NULL, `unread` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_chat_threads_updated_at_millis` ON `chat_threads` (`updated_at_millis`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `attachments_meta` (`id` TEXT NOT NULL, `owner_type` TEXT NOT NULL, `owner_id` TEXT NOT NULL, `uri` TEXT NOT NULL, `mime` TEXT NOT NULL, `size_bytes` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_attachments_meta_owner_type_owner_id` ON `attachments_meta` (`owner_type`, `owner_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '82a7738b182d68f6cabc8b2d16b9e9d6')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `messages`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `departments`");
        db.execSQL("DROP TABLE IF EXISTS `user_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `news_articles`");
        db.execSQL("DROP TABLE IF EXISTS `news_comments`");
        db.execSQL("DROP TABLE IF EXISTS `polls`");
        db.execSQL("DROP TABLE IF EXISTS `poll_options`");
        db.execSQL("DROP TABLE IF EXISTS `notifications_inbox`");
        db.execSQL("DROP TABLE IF EXISTS `chat_threads`");
        db.execSQL("DROP TABLE IF EXISTS `attachments_meta`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsMessages = new HashMap<String, TableInfo.Column>(8);
        _columnsMessages.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("sender_id", new TableInfo.Column("sender_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("recipient_id", new TableInfo.Column("recipient_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("group_id", new TableInfo.Column("group_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("message_text", new TableInfo.Column("message_text", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("file_path", new TableInfo.Column("file_path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("is_read", new TableInfo.Column("is_read", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMessages = new TableInfo("messages", _columnsMessages, _foreignKeysMessages, _indicesMessages);
        final TableInfo _existingMessages = TableInfo.read(db, "messages");
        if (!_infoMessages.equals(_existingMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "messages(com.example.messenger.data.entities.MessageEntity).\n"
                  + " Expected:\n" + _infoMessages + "\n"
                  + " Found:\n" + _existingMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password_hash", new TableInfo.Column("password_hash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("salt", new TableInfo.Column("salt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isOnline", new TableInfo.Column("isOnline", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.messenger.data.entities.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsDepartments = new HashMap<String, TableInfo.Column>(2);
        _columnsDepartments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDepartments.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDepartments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDepartments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDepartments = new TableInfo("departments", _columnsDepartments, _foreignKeysDepartments, _indicesDepartments);
        final TableInfo _existingDepartments = TableInfo.read(db, "departments");
        if (!_infoDepartments.equals(_existingDepartments)) {
          return new RoomOpenHelper.ValidationResult(false, "departments(com.example.messenger.data.entities.DepartmentEntity).\n"
                  + " Expected:\n" + _infoDepartments + "\n"
                  + " Found:\n" + _existingDepartments);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfiles = new HashMap<String, TableInfo.Column>(16);
        _columnsUserProfiles.put("userId", new TableInfo.Column("userId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("first_name", new TableInfo.Column("first_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("last_name", new TableInfo.Column("last_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("middle_name", new TableInfo.Column("middle_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("photo_uri", new TableInfo.Column("photo_uri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("position", new TableInfo.Column("position", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("department_id", new TableInfo.Column("department_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("birth_date_millis", new TableInfo.Column("birth_date_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("status_text", new TableInfo.Column("status_text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("about", new TableInfo.Column("about", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("registered_at_millis", new TableInfo.Column("registered_at_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("is_online", new TableInfo.Column("is_online", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("account_locked", new TableInfo.Column("account_locked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfiles = new TableInfo("user_profiles", _columnsUserProfiles, _foreignKeysUserProfiles, _indicesUserProfiles);
        final TableInfo _existingUserProfiles = TableInfo.read(db, "user_profiles");
        if (!_infoUserProfiles.equals(_existingUserProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profiles(com.example.messenger.data.entities.UserProfileEntity).\n"
                  + " Expected:\n" + _infoUserProfiles + "\n"
                  + " Found:\n" + _existingUserProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsNewsArticles = new HashMap<String, TableInfo.Column>(10);
        _columnsNewsArticles.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("image_url", new TableInfo.Column("image_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("author_user_id", new TableInfo.Column("author_user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("department_id", new TableInfo.Column("department_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("pinned", new TableInfo.Column("pinned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("created_at_millis", new TableInfo.Column("created_at_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("like_count", new TableInfo.Column("like_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsArticles.put("announcement", new TableInfo.Column("announcement", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNewsArticles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNewsArticles = new HashSet<TableInfo.Index>(1);
        _indicesNewsArticles.add(new TableInfo.Index("index_news_articles_created_at_millis", false, Arrays.asList("created_at_millis"), Arrays.asList("ASC")));
        final TableInfo _infoNewsArticles = new TableInfo("news_articles", _columnsNewsArticles, _foreignKeysNewsArticles, _indicesNewsArticles);
        final TableInfo _existingNewsArticles = TableInfo.read(db, "news_articles");
        if (!_infoNewsArticles.equals(_existingNewsArticles)) {
          return new RoomOpenHelper.ValidationResult(false, "news_articles(com.example.messenger.data.entities.NewsArticleEntity).\n"
                  + " Expected:\n" + _infoNewsArticles + "\n"
                  + " Found:\n" + _existingNewsArticles);
        }
        final HashMap<String, TableInfo.Column> _columnsNewsComments = new HashMap<String, TableInfo.Column>(5);
        _columnsNewsComments.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsComments.put("news_id", new TableInfo.Column("news_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsComments.put("author_user_id", new TableInfo.Column("author_user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsComments.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewsComments.put("created_at_millis", new TableInfo.Column("created_at_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNewsComments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNewsComments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNewsComments = new TableInfo("news_comments", _columnsNewsComments, _foreignKeysNewsComments, _indicesNewsComments);
        final TableInfo _existingNewsComments = TableInfo.read(db, "news_comments");
        if (!_infoNewsComments.equals(_existingNewsComments)) {
          return new RoomOpenHelper.ValidationResult(false, "news_comments(com.example.messenger.data.entities.NewsCommentEntity).\n"
                  + " Expected:\n" + _infoNewsComments + "\n"
                  + " Found:\n" + _existingNewsComments);
        }
        final HashMap<String, TableInfo.Column> _columnsPolls = new HashMap<String, TableInfo.Column>(5);
        _columnsPolls.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolls.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolls.put("created_by_user_id", new TableInfo.Column("created_by_user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolls.put("created_at_millis", new TableInfo.Column("created_at_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolls.put("closed", new TableInfo.Column("closed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPolls = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPolls = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPolls = new TableInfo("polls", _columnsPolls, _foreignKeysPolls, _indicesPolls);
        final TableInfo _existingPolls = TableInfo.read(db, "polls");
        if (!_infoPolls.equals(_existingPolls)) {
          return new RoomOpenHelper.ValidationResult(false, "polls(com.example.messenger.data.entities.PollEntity).\n"
                  + " Expected:\n" + _infoPolls + "\n"
                  + " Found:\n" + _existingPolls);
        }
        final HashMap<String, TableInfo.Column> _columnsPollOptions = new HashMap<String, TableInfo.Column>(4);
        _columnsPollOptions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPollOptions.put("poll_id", new TableInfo.Column("poll_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPollOptions.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPollOptions.put("votes", new TableInfo.Column("votes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPollOptions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPollOptions = new HashSet<TableInfo.Index>(1);
        _indicesPollOptions.add(new TableInfo.Index("index_poll_options_poll_id", false, Arrays.asList("poll_id"), Arrays.asList("ASC")));
        final TableInfo _infoPollOptions = new TableInfo("poll_options", _columnsPollOptions, _foreignKeysPollOptions, _indicesPollOptions);
        final TableInfo _existingPollOptions = TableInfo.read(db, "poll_options");
        if (!_infoPollOptions.equals(_existingPollOptions)) {
          return new RoomOpenHelper.ValidationResult(false, "poll_options(com.example.messenger.data.entities.PollOptionEntity).\n"
                  + " Expected:\n" + _infoPollOptions + "\n"
                  + " Found:\n" + _existingPollOptions);
        }
        final HashMap<String, TableInfo.Column> _columnsNotificationsInbox = new HashMap<String, TableInfo.Column>(5);
        _columnsNotificationsInbox.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationsInbox.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationsInbox.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationsInbox.put("created_at_millis", new TableInfo.Column("created_at_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationsInbox.put("read", new TableInfo.Column("read", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotificationsInbox = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotificationsInbox = new HashSet<TableInfo.Index>(1);
        _indicesNotificationsInbox.add(new TableInfo.Index("index_notifications_inbox_created_at_millis", false, Arrays.asList("created_at_millis"), Arrays.asList("ASC")));
        final TableInfo _infoNotificationsInbox = new TableInfo("notifications_inbox", _columnsNotificationsInbox, _foreignKeysNotificationsInbox, _indicesNotificationsInbox);
        final TableInfo _existingNotificationsInbox = TableInfo.read(db, "notifications_inbox");
        if (!_infoNotificationsInbox.equals(_existingNotificationsInbox)) {
          return new RoomOpenHelper.ValidationResult(false, "notifications_inbox(com.example.messenger.data.entities.InboxNotificationEntity).\n"
                  + " Expected:\n" + _infoNotificationsInbox + "\n"
                  + " Found:\n" + _existingNotificationsInbox);
        }
        final HashMap<String, TableInfo.Column> _columnsChatThreads = new HashMap<String, TableInfo.Column>(8);
        _columnsChatThreads.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("peer_user_id", new TableInfo.Column("peer_user_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("department_id", new TableInfo.Column("department_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("last_message_preview", new TableInfo.Column("last_message_preview", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("updated_at_millis", new TableInfo.Column("updated_at_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatThreads.put("unread", new TableInfo.Column("unread", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatThreads = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatThreads = new HashSet<TableInfo.Index>(1);
        _indicesChatThreads.add(new TableInfo.Index("index_chat_threads_updated_at_millis", false, Arrays.asList("updated_at_millis"), Arrays.asList("ASC")));
        final TableInfo _infoChatThreads = new TableInfo("chat_threads", _columnsChatThreads, _foreignKeysChatThreads, _indicesChatThreads);
        final TableInfo _existingChatThreads = TableInfo.read(db, "chat_threads");
        if (!_infoChatThreads.equals(_existingChatThreads)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_threads(com.example.messenger.data.entities.ChatThreadEntity).\n"
                  + " Expected:\n" + _infoChatThreads + "\n"
                  + " Found:\n" + _existingChatThreads);
        }
        final HashMap<String, TableInfo.Column> _columnsAttachmentsMeta = new HashMap<String, TableInfo.Column>(6);
        _columnsAttachmentsMeta.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttachmentsMeta.put("owner_type", new TableInfo.Column("owner_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttachmentsMeta.put("owner_id", new TableInfo.Column("owner_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttachmentsMeta.put("uri", new TableInfo.Column("uri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttachmentsMeta.put("mime", new TableInfo.Column("mime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttachmentsMeta.put("size_bytes", new TableInfo.Column("size_bytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttachmentsMeta = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAttachmentsMeta = new HashSet<TableInfo.Index>(1);
        _indicesAttachmentsMeta.add(new TableInfo.Index("index_attachments_meta_owner_type_owner_id", false, Arrays.asList("owner_type", "owner_id"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoAttachmentsMeta = new TableInfo("attachments_meta", _columnsAttachmentsMeta, _foreignKeysAttachmentsMeta, _indicesAttachmentsMeta);
        final TableInfo _existingAttachmentsMeta = TableInfo.read(db, "attachments_meta");
        if (!_infoAttachmentsMeta.equals(_existingAttachmentsMeta)) {
          return new RoomOpenHelper.ValidationResult(false, "attachments_meta(com.example.messenger.data.entities.AttachmentMetaEntity).\n"
                  + " Expected:\n" + _infoAttachmentsMeta + "\n"
                  + " Found:\n" + _existingAttachmentsMeta);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "82a7738b182d68f6cabc8b2d16b9e9d6", "b66b968d3ed4bae054d8b229d3a2635e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "messages","users","departments","user_profiles","news_articles","news_comments","polls","poll_options","notifications_inbox","chat_threads","attachments_meta");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `messages`");
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `departments`");
      _db.execSQL("DELETE FROM `user_profiles`");
      _db.execSQL("DELETE FROM `news_articles`");
      _db.execSQL("DELETE FROM `news_comments`");
      _db.execSQL("DELETE FROM `polls`");
      _db.execSQL("DELETE FROM `poll_options`");
      _db.execSQL("DELETE FROM `notifications_inbox`");
      _db.execSQL("DELETE FROM `chat_threads`");
      _db.execSQL("DELETE FROM `attachments_meta`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MessageDao.class, MessageDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DepartmentDao.class, DepartmentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NewsArticleDao.class, NewsArticleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NewsCommentDao.class, NewsCommentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PollDao.class, PollDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(InboxNotificationDao.class, InboxNotificationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChatThreadDao.class, ChatThreadDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AttachmentDao.class, AttachmentDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MessageDao messageDao() {
    if (_messageDao != null) {
      return _messageDao;
    } else {
      synchronized(this) {
        if(_messageDao == null) {
          _messageDao = new MessageDao_Impl(this);
        }
        return _messageDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public DepartmentDao departmentDao() {
    if (_departmentDao != null) {
      return _departmentDao;
    } else {
      synchronized(this) {
        if(_departmentDao == null) {
          _departmentDao = new DepartmentDao_Impl(this);
        }
        return _departmentDao;
      }
    }
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }

  @Override
  public NewsArticleDao newsArticleDao() {
    if (_newsArticleDao != null) {
      return _newsArticleDao;
    } else {
      synchronized(this) {
        if(_newsArticleDao == null) {
          _newsArticleDao = new NewsArticleDao_Impl(this);
        }
        return _newsArticleDao;
      }
    }
  }

  @Override
  public NewsCommentDao newsCommentDao() {
    if (_newsCommentDao != null) {
      return _newsCommentDao;
    } else {
      synchronized(this) {
        if(_newsCommentDao == null) {
          _newsCommentDao = new NewsCommentDao_Impl(this);
        }
        return _newsCommentDao;
      }
    }
  }

  @Override
  public PollDao pollDao() {
    if (_pollDao != null) {
      return _pollDao;
    } else {
      synchronized(this) {
        if(_pollDao == null) {
          _pollDao = new PollDao_Impl(this);
        }
        return _pollDao;
      }
    }
  }

  @Override
  public InboxNotificationDao inboxNotificationDao() {
    if (_inboxNotificationDao != null) {
      return _inboxNotificationDao;
    } else {
      synchronized(this) {
        if(_inboxNotificationDao == null) {
          _inboxNotificationDao = new InboxNotificationDao_Impl(this);
        }
        return _inboxNotificationDao;
      }
    }
  }

  @Override
  public ChatThreadDao chatThreadDao() {
    if (_chatThreadDao != null) {
      return _chatThreadDao;
    } else {
      synchronized(this) {
        if(_chatThreadDao == null) {
          _chatThreadDao = new ChatThreadDao_Impl(this);
        }
        return _chatThreadDao;
      }
    }
  }

  @Override
  public AttachmentDao attachmentDao() {
    if (_attachmentDao != null) {
      return _attachmentDao;
    } else {
      synchronized(this) {
        if(_attachmentDao == null) {
          _attachmentDao = new AttachmentDao_Impl(this);
        }
        return _attachmentDao;
      }
    }
  }
}
