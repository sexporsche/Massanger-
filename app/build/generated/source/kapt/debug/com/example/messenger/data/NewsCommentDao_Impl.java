package com.example.messenger.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.messenger.data.entities.NewsCommentEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class NewsCommentDao_Impl implements NewsCommentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NewsCommentEntity> __insertionAdapterOfNewsCommentEntity;

  public NewsCommentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNewsCommentEntity = new EntityInsertionAdapter<NewsCommentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `news_comments` (`id`,`news_id`,`author_user_id`,`body`,`created_at_millis`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NewsCommentEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getNewsId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNewsId());
        }
        statement.bindLong(3, entity.getAuthorUserId());
        if (entity.getBody() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getBody());
        }
        statement.bindLong(5, entity.getCreatedAtMillis());
      }
    };
  }

  @Override
  public Object insert(final NewsCommentEntity comment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNewsCommentEntity.insert(comment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NewsCommentEntity>> observeForNews(final String newsId) {
    final String _sql = "SELECT * FROM news_comments WHERE news_id = ? ORDER BY created_at_millis ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (newsId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, newsId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"news_comments"}, new Callable<List<NewsCommentEntity>>() {
      @Override
      @NonNull
      public List<NewsCommentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNewsId = CursorUtil.getColumnIndexOrThrow(_cursor, "news_id");
          final int _cursorIndexOfAuthorUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "author_user_id");
          final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
          final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_millis");
          final List<NewsCommentEntity> _result = new ArrayList<NewsCommentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NewsCommentEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNewsId;
            if (_cursor.isNull(_cursorIndexOfNewsId)) {
              _tmpNewsId = null;
            } else {
              _tmpNewsId = _cursor.getString(_cursorIndexOfNewsId);
            }
            final int _tmpAuthorUserId;
            _tmpAuthorUserId = _cursor.getInt(_cursorIndexOfAuthorUserId);
            final String _tmpBody;
            if (_cursor.isNull(_cursorIndexOfBody)) {
              _tmpBody = null;
            } else {
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
            }
            final long _tmpCreatedAtMillis;
            _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
            _item = new NewsCommentEntity(_tmpId,_tmpNewsId,_tmpAuthorUserId,_tmpBody,_tmpCreatedAtMillis);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
