package com.example.messenger.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.messenger.data.entities.PollEntity;
import com.example.messenger.data.entities.PollOptionEntity;
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
public final class PollDao_Impl implements PollDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PollEntity> __insertionAdapterOfPollEntity;

  private final EntityInsertionAdapter<PollOptionEntity> __insertionAdapterOfPollOptionEntity;

  private final SharedSQLiteStatement __preparedStmtOfVoteOption;

  public PollDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPollEntity = new EntityInsertionAdapter<PollEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `polls` (`id`,`title`,`created_by_user_id`,`created_at_millis`,`closed`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PollEntity entity) {
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
        statement.bindLong(3, entity.getCreatedByUserId());
        statement.bindLong(4, entity.getCreatedAtMillis());
        final int _tmp = entity.getClosed() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__insertionAdapterOfPollOptionEntity = new EntityInsertionAdapter<PollOptionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `poll_options` (`id`,`poll_id`,`label`,`votes`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PollOptionEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getPollId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPollId());
        }
        if (entity.getLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLabel());
        }
        statement.bindLong(4, entity.getVotes());
      }
    };
    this.__preparedStmtOfVoteOption = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE poll_options SET votes = votes + 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPoll(final PollEntity poll, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPollEntity.insert(poll);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOptions(final List<PollOptionEntity> options,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPollOptionEntity.insert(options);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object replacePollWithOptions(final PollEntity poll, final List<PollOptionEntity> options,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> PollDao.DefaultImpls.replacePollWithOptions(PollDao_Impl.this, poll, options, __cont), $completion);
  }

  @Override
  public Object voteOption(final String optionId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfVoteOption.acquire();
        int _argIndex = 1;
        if (optionId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, optionId);
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
          __preparedStmtOfVoteOption.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PollEntity>> observePolls() {
    final String _sql = "SELECT * FROM polls ORDER BY created_at_millis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"polls"}, new Callable<List<PollEntity>>() {
      @Override
      @NonNull
      public List<PollEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCreatedByUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "created_by_user_id");
          final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at_millis");
          final int _cursorIndexOfClosed = CursorUtil.getColumnIndexOrThrow(_cursor, "closed");
          final List<PollEntity> _result = new ArrayList<PollEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PollEntity _item;
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
            final int _tmpCreatedByUserId;
            _tmpCreatedByUserId = _cursor.getInt(_cursorIndexOfCreatedByUserId);
            final long _tmpCreatedAtMillis;
            _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
            final boolean _tmpClosed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfClosed);
            _tmpClosed = _tmp != 0;
            _item = new PollEntity(_tmpId,_tmpTitle,_tmpCreatedByUserId,_tmpCreatedAtMillis,_tmpClosed);
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
  public Flow<List<PollOptionEntity>> observeOptions(final String pollId) {
    final String _sql = "SELECT * FROM poll_options WHERE poll_id = ? ORDER BY votes DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (pollId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, pollId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"poll_options"}, new Callable<List<PollOptionEntity>>() {
      @Override
      @NonNull
      public List<PollOptionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPollId = CursorUtil.getColumnIndexOrThrow(_cursor, "poll_id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfVotes = CursorUtil.getColumnIndexOrThrow(_cursor, "votes");
          final List<PollOptionEntity> _result = new ArrayList<PollOptionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PollOptionEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPollId;
            if (_cursor.isNull(_cursorIndexOfPollId)) {
              _tmpPollId = null;
            } else {
              _tmpPollId = _cursor.getString(_cursorIndexOfPollId);
            }
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final int _tmpVotes;
            _tmpVotes = _cursor.getInt(_cursorIndexOfVotes);
            _item = new PollOptionEntity(_tmpId,_tmpPollId,_tmpLabel,_tmpVotes);
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
