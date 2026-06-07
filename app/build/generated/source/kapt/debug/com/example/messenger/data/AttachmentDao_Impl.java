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
import com.example.messenger.data.entities.AttachmentMetaEntity;
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
public final class AttachmentDao_Impl implements AttachmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AttachmentMetaEntity> __insertionAdapterOfAttachmentMetaEntity;

  public AttachmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAttachmentMetaEntity = new EntityInsertionAdapter<AttachmentMetaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `attachments_meta` (`id`,`owner_type`,`owner_id`,`uri`,`mime`,`size_bytes`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AttachmentMetaEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getOwnerType() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getOwnerType());
        }
        if (entity.getOwnerId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getOwnerId());
        }
        if (entity.getUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUri());
        }
        if (entity.getMime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getMime());
        }
        statement.bindLong(6, entity.getSizeBytes());
      }
    };
  }

  @Override
  public Object insert(final AttachmentMetaEntity item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAttachmentMetaEntity.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AttachmentMetaEntity>> observeForOwner(final String type, final String ownerId) {
    final String _sql = "SELECT * FROM attachments_meta WHERE owner_type = ? AND owner_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    _argIndex = 2;
    if (ownerId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ownerId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attachments_meta"}, new Callable<List<AttachmentMetaEntity>>() {
      @Override
      @NonNull
      public List<AttachmentMetaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOwnerType = CursorUtil.getColumnIndexOrThrow(_cursor, "owner_type");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "owner_id");
          final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
          final int _cursorIndexOfMime = CursorUtil.getColumnIndexOrThrow(_cursor, "mime");
          final int _cursorIndexOfSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "size_bytes");
          final List<AttachmentMetaEntity> _result = new ArrayList<AttachmentMetaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AttachmentMetaEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpOwnerType;
            if (_cursor.isNull(_cursorIndexOfOwnerType)) {
              _tmpOwnerType = null;
            } else {
              _tmpOwnerType = _cursor.getString(_cursorIndexOfOwnerType);
            }
            final String _tmpOwnerId;
            if (_cursor.isNull(_cursorIndexOfOwnerId)) {
              _tmpOwnerId = null;
            } else {
              _tmpOwnerId = _cursor.getString(_cursorIndexOfOwnerId);
            }
            final String _tmpUri;
            if (_cursor.isNull(_cursorIndexOfUri)) {
              _tmpUri = null;
            } else {
              _tmpUri = _cursor.getString(_cursorIndexOfUri);
            }
            final String _tmpMime;
            if (_cursor.isNull(_cursorIndexOfMime)) {
              _tmpMime = null;
            } else {
              _tmpMime = _cursor.getString(_cursorIndexOfMime);
            }
            final long _tmpSizeBytes;
            _tmpSizeBytes = _cursor.getLong(_cursorIndexOfSizeBytes);
            _item = new AttachmentMetaEntity(_tmpId,_tmpOwnerType,_tmpOwnerId,_tmpUri,_tmpMime,_tmpSizeBytes);
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
