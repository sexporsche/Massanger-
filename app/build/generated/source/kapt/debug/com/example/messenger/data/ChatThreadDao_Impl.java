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
import com.example.messenger.data.entities.ChatThreadEntity;
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
public final class ChatThreadDao_Impl implements ChatThreadDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatThreadEntity> __insertionAdapterOfChatThreadEntity;

  public ChatThreadDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatThreadEntity = new EntityInsertionAdapter<ChatThreadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `chat_threads` (`id`,`title`,`type`,`peer_user_id`,`department_id`,`last_message_preview`,`updated_at_millis`,`unread`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatThreadEntity entity) {
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
        if (entity.getType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getType());
        }
        if (entity.getPeerUserId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getPeerUserId());
        }
        if (entity.getDepartmentId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getDepartmentId());
        }
        if (entity.getLastMessagePreview() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLastMessagePreview());
        }
        statement.bindLong(7, entity.getUpdatedAtMillis());
        statement.bindLong(8, entity.getUnread());
      }
    };
  }

  @Override
  public Object upsertAll(final List<ChatThreadEntity> threads,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChatThreadEntity.insert(threads);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ChatThreadEntity>> observeThreads() {
    final String _sql = "SELECT * FROM chat_threads ORDER BY updated_at_millis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"chat_threads"}, new Callable<List<ChatThreadEntity>>() {
      @Override
      @NonNull
      public List<ChatThreadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPeerUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "peer_user_id");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfLastMessagePreview = CursorUtil.getColumnIndexOrThrow(_cursor, "last_message_preview");
          final int _cursorIndexOfUpdatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at_millis");
          final int _cursorIndexOfUnread = CursorUtil.getColumnIndexOrThrow(_cursor, "unread");
          final List<ChatThreadEntity> _result = new ArrayList<ChatThreadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatThreadEntity _item;
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
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final Integer _tmpPeerUserId;
            if (_cursor.isNull(_cursorIndexOfPeerUserId)) {
              _tmpPeerUserId = null;
            } else {
              _tmpPeerUserId = _cursor.getInt(_cursorIndexOfPeerUserId);
            }
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final String _tmpLastMessagePreview;
            if (_cursor.isNull(_cursorIndexOfLastMessagePreview)) {
              _tmpLastMessagePreview = null;
            } else {
              _tmpLastMessagePreview = _cursor.getString(_cursorIndexOfLastMessagePreview);
            }
            final long _tmpUpdatedAtMillis;
            _tmpUpdatedAtMillis = _cursor.getLong(_cursorIndexOfUpdatedAtMillis);
            final int _tmpUnread;
            _tmpUnread = _cursor.getInt(_cursorIndexOfUnread);
            _item = new ChatThreadEntity(_tmpId,_tmpTitle,_tmpType,_tmpPeerUserId,_tmpDepartmentId,_tmpLastMessagePreview,_tmpUpdatedAtMillis,_tmpUnread);
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
