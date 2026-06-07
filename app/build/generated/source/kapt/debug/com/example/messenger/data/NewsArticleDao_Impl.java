package com.example.messenger.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.messenger.data.entities.NewsArticleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NewsArticleDao_Impl implements NewsArticleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NewsArticleEntity> __insertionAdapterOfNewsArticleEntity;

  private final SharedSQLiteStatement __preparedStmtOfIncrementLike;

  public NewsArticleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNewsArticleEntity = new EntityInsertionAdapter<NewsArticleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `news_articles` (`id`,`title`,`body`,`image_url`,`author_user_id`,`department_id`,`pinned`,`created_at_millis`,`like_count`,`announcement`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NewsArticleEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getBody() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBody());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getImageUrl());
        }
        statement.bindLong(5, entity.getAuthorUserId());
        if (entity.getDepartmentId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDepartmentId());
        }
        final int _tmp = entity.getPinned() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAtMillis());
        statement.bindLong(9, entity.getLikeCount());
        final int _tmp_1 = entity.getAnnouncement() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
      }
    };
    this.__preparedStmtOfIncrementLike = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE news_articles SET like_count = like_count + 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsertAll(final List<NewsArticleEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNewsArticleEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsert(final NewsArticleEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNewsArticleEntity.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementLike(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementLike.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementLike.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM news_articles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NewsArticleEntity>> observeArticles() {
    final String _sql = "SELECT * FROM news_articles ORDER BY pinned DESC, created_at_millis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"news_articles"}, new Callable<List<NewsArticleEntity>>() {
      @Override
      @NonNull
      public List<NewsArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_url");
          final int _cursorIndexOfAuthorUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "author_user_id");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
          final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_millis");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "like_count");
          final int _cursorIndexOfAnnouncement = CursorUtil.getColumnIndexOrThrow(_cursor, "announcement");
          final List<NewsArticleEntity> _result = new ArrayList<NewsArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NewsArticleEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpBody;
            if (_cursor.isNull(_cursorIndexOfBody)) {
              _tmpBody = null;
            } else {
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpAuthorUserId;
            _tmpAuthorUserId = _cursor.getInt(_cursorIndexOfAuthorUserId);
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final boolean _tmpPinned;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPinned);
            _tmpPinned = _tmp != 0;
            final long _tmpCreatedAtMillis;
            _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            final boolean _tmpAnnouncement;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAnnouncement);
            _tmpAnnouncement = _tmp_1 != 0;
            _item = new NewsArticleEntity(_tmpId,_tmpTitle,_tmpBody,_tmpImageUrl,_tmpAuthorUserId,_tmpDepartmentId,_tmpPinned,_tmpCreatedAtMillis,_tmpLikeCount,_tmpAnnouncement);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<NewsArticleEntity> observeArticle(final String id) {
    final String _sql = "SELECT * FROM news_articles WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"news_articles"}, new Callable<NewsArticleEntity>() {
      @Override
      @Nullable
      public NewsArticleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_url");
          final int _cursorIndexOfAuthorUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "author_user_id");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
          final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_millis");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "like_count");
          final int _cursorIndexOfAnnouncement = CursorUtil.getColumnIndexOrThrow(_cursor, "announcement");
          final NewsArticleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpBody;
            if (_cursor.isNull(_cursorIndexOfBody)) {
              _tmpBody = null;
            } else {
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpAuthorUserId;
            _tmpAuthorUserId = _cursor.getInt(_cursorIndexOfAuthorUserId);
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final boolean _tmpPinned;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPinned);
            _tmpPinned = _tmp != 0;
            final long _tmpCreatedAtMillis;
            _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            final boolean _tmpAnnouncement;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAnnouncement);
            _tmpAnnouncement = _tmp_1 != 0;
            _result = new NewsArticleEntity(_tmpId,_tmpTitle,_tmpBody,_tmpImageUrl,_tmpAuthorUserId,_tmpDepartmentId,_tmpPinned,_tmpCreatedAtMillis,_tmpLikeCount,_tmpAnnouncement);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final String id,
      final Continuation<? super NewsArticleEntity> $completion) {
    final String _sql = "SELECT * FROM news_articles WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NewsArticleEntity>() {
      @Override
      @Nullable
      public NewsArticleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_url");
          final int _cursorIndexOfAuthorUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "author_user_id");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
          final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_millis");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "like_count");
          final int _cursorIndexOfAnnouncement = CursorUtil.getColumnIndexOrThrow(_cursor, "announcement");
          final NewsArticleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpBody;
            if (_cursor.isNull(_cursorIndexOfBody)) {
              _tmpBody = null;
            } else {
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpAuthorUserId;
            _tmpAuthorUserId = _cursor.getInt(_cursorIndexOfAuthorUserId);
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final boolean _tmpPinned;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPinned);
            _tmpPinned = _tmp != 0;
            final long _tmpCreatedAtMillis;
            _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            final boolean _tmpAnnouncement;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAnnouncement);
            _tmpAnnouncement = _tmp_1 != 0;
            _result = new NewsArticleEntity(_tmpId,_tmpTitle,_tmpBody,_tmpImageUrl,_tmpAuthorUserId,_tmpDepartmentId,_tmpPinned,_tmpCreatedAtMillis,_tmpLikeCount,_tmpAnnouncement);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
