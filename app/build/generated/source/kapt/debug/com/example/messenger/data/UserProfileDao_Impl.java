package com.example.messenger.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.messenger.data.entities.UserProfileEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfileEntity> __insertionAdapterOfUserProfileEntity;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfileEntity = new EntityInsertionAdapter<UserProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profiles` (`userId`,`first_name`,`last_name`,`middle_name`,`photo_uri`,`position`,`department_id`,`email`,`phone`,`birth_date_millis`,`status_text`,`about`,`registered_at_millis`,`role`,`is_online`,`account_locked`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfileEntity entity) {
        statement.bindLong(1, entity.getUserId());
        if (entity.getFirstName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirstName());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastName());
        }
        if (entity.getMiddleName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMiddleName());
        }
        if (entity.getPhotoUri() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPhotoUri());
        }
        if (entity.getPosition() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPosition());
        }
        if (entity.getDepartmentId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getDepartmentId());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getEmail());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPhone());
        }
        if (entity.getBirthDateMillis() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getBirthDateMillis());
        }
        if (entity.getStatusText() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getStatusText());
        }
        if (entity.getAbout() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getAbout());
        }
        statement.bindLong(13, entity.getRegisteredAtMillis());
        if (entity.getRole() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getRole());
        }
        final int _tmp = entity.isOnline() ? 1 : 0;
        statement.bindLong(15, _tmp);
        final int _tmp_1 = entity.getAccountLocked() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
      }
    };
  }

  @Override
  public Object upsert(final UserProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfileEntity.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserProfileEntity> observeProfile(final int id) {
    final String _sql = "SELECT * FROM user_profiles WHERE userId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profiles"}, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
          final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middle_name");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_uri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBirthDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "birth_date_millis");
          final int _cursorIndexOfStatusText = CursorUtil.getColumnIndexOrThrow(_cursor, "status_text");
          final int _cursorIndexOfAbout = CursorUtil.getColumnIndexOrThrow(_cursor, "about");
          final int _cursorIndexOfRegisteredAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "registered_at_millis");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "is_online");
          final int _cursorIndexOfAccountLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "account_locked");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpFirstName;
            if (_cursor.isNull(_cursorIndexOfFirstName)) {
              _tmpFirstName = null;
            } else {
              _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            }
            final String _tmpLastName;
            if (_cursor.isNull(_cursorIndexOfLastName)) {
              _tmpLastName = null;
            } else {
              _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpPosition;
            if (_cursor.isNull(_cursorIndexOfPosition)) {
              _tmpPosition = null;
            } else {
              _tmpPosition = _cursor.getString(_cursorIndexOfPosition);
            }
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Long _tmpBirthDateMillis;
            if (_cursor.isNull(_cursorIndexOfBirthDateMillis)) {
              _tmpBirthDateMillis = null;
            } else {
              _tmpBirthDateMillis = _cursor.getLong(_cursorIndexOfBirthDateMillis);
            }
            final String _tmpStatusText;
            if (_cursor.isNull(_cursorIndexOfStatusText)) {
              _tmpStatusText = null;
            } else {
              _tmpStatusText = _cursor.getString(_cursorIndexOfStatusText);
            }
            final String _tmpAbout;
            if (_cursor.isNull(_cursorIndexOfAbout)) {
              _tmpAbout = null;
            } else {
              _tmpAbout = _cursor.getString(_cursorIndexOfAbout);
            }
            final long _tmpRegisteredAtMillis;
            _tmpRegisteredAtMillis = _cursor.getLong(_cursorIndexOfRegisteredAtMillis);
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final boolean _tmpAccountLocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAccountLocked);
            _tmpAccountLocked = _tmp_1 != 0;
            _result = new UserProfileEntity(_tmpUserId,_tmpFirstName,_tmpLastName,_tmpMiddleName,_tmpPhotoUri,_tmpPosition,_tmpDepartmentId,_tmpEmail,_tmpPhone,_tmpBirthDateMillis,_tmpStatusText,_tmpAbout,_tmpRegisteredAtMillis,_tmpRole,_tmpIsOnline,_tmpAccountLocked);
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
  public Object getProfile(final int id,
      final Continuation<? super UserProfileEntity> $completion) {
    final String _sql = "SELECT * FROM user_profiles WHERE userId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfileEntity>() {
      @Override
      @Nullable
      public UserProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
          final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middle_name");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_uri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBirthDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "birth_date_millis");
          final int _cursorIndexOfStatusText = CursorUtil.getColumnIndexOrThrow(_cursor, "status_text");
          final int _cursorIndexOfAbout = CursorUtil.getColumnIndexOrThrow(_cursor, "about");
          final int _cursorIndexOfRegisteredAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "registered_at_millis");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "is_online");
          final int _cursorIndexOfAccountLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "account_locked");
          final UserProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpFirstName;
            if (_cursor.isNull(_cursorIndexOfFirstName)) {
              _tmpFirstName = null;
            } else {
              _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            }
            final String _tmpLastName;
            if (_cursor.isNull(_cursorIndexOfLastName)) {
              _tmpLastName = null;
            } else {
              _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpPosition;
            if (_cursor.isNull(_cursorIndexOfPosition)) {
              _tmpPosition = null;
            } else {
              _tmpPosition = _cursor.getString(_cursorIndexOfPosition);
            }
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Long _tmpBirthDateMillis;
            if (_cursor.isNull(_cursorIndexOfBirthDateMillis)) {
              _tmpBirthDateMillis = null;
            } else {
              _tmpBirthDateMillis = _cursor.getLong(_cursorIndexOfBirthDateMillis);
            }
            final String _tmpStatusText;
            if (_cursor.isNull(_cursorIndexOfStatusText)) {
              _tmpStatusText = null;
            } else {
              _tmpStatusText = _cursor.getString(_cursorIndexOfStatusText);
            }
            final String _tmpAbout;
            if (_cursor.isNull(_cursorIndexOfAbout)) {
              _tmpAbout = null;
            } else {
              _tmpAbout = _cursor.getString(_cursorIndexOfAbout);
            }
            final long _tmpRegisteredAtMillis;
            _tmpRegisteredAtMillis = _cursor.getLong(_cursorIndexOfRegisteredAtMillis);
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final boolean _tmpAccountLocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAccountLocked);
            _tmpAccountLocked = _tmp_1 != 0;
            _result = new UserProfileEntity(_tmpUserId,_tmpFirstName,_tmpLastName,_tmpMiddleName,_tmpPhotoUri,_tmpPosition,_tmpDepartmentId,_tmpEmail,_tmpPhone,_tmpBirthDateMillis,_tmpStatusText,_tmpAbout,_tmpRegisteredAtMillis,_tmpRole,_tmpIsOnline,_tmpAccountLocked);
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
  public Flow<List<UserProfileEntity>> observeAllProfiles() {
    final String _sql = "SELECT * FROM user_profiles ORDER BY last_name COLLATE NOCASE ASC, first_name COLLATE NOCASE ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profiles"}, new Callable<List<UserProfileEntity>>() {
      @Override
      @NonNull
      public List<UserProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
          final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middle_name");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_uri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfDepartmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "department_id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfBirthDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "birth_date_millis");
          final int _cursorIndexOfStatusText = CursorUtil.getColumnIndexOrThrow(_cursor, "status_text");
          final int _cursorIndexOfAbout = CursorUtil.getColumnIndexOrThrow(_cursor, "about");
          final int _cursorIndexOfRegisteredAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "registered_at_millis");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "is_online");
          final int _cursorIndexOfAccountLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "account_locked");
          final List<UserProfileEntity> _result = new ArrayList<UserProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserProfileEntity _item;
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpFirstName;
            if (_cursor.isNull(_cursorIndexOfFirstName)) {
              _tmpFirstName = null;
            } else {
              _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            }
            final String _tmpLastName;
            if (_cursor.isNull(_cursorIndexOfLastName)) {
              _tmpLastName = null;
            } else {
              _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            }
            final String _tmpMiddleName;
            if (_cursor.isNull(_cursorIndexOfMiddleName)) {
              _tmpMiddleName = null;
            } else {
              _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpPosition;
            if (_cursor.isNull(_cursorIndexOfPosition)) {
              _tmpPosition = null;
            } else {
              _tmpPosition = _cursor.getString(_cursorIndexOfPosition);
            }
            final Integer _tmpDepartmentId;
            if (_cursor.isNull(_cursorIndexOfDepartmentId)) {
              _tmpDepartmentId = null;
            } else {
              _tmpDepartmentId = _cursor.getInt(_cursorIndexOfDepartmentId);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final Long _tmpBirthDateMillis;
            if (_cursor.isNull(_cursorIndexOfBirthDateMillis)) {
              _tmpBirthDateMillis = null;
            } else {
              _tmpBirthDateMillis = _cursor.getLong(_cursorIndexOfBirthDateMillis);
            }
            final String _tmpStatusText;
            if (_cursor.isNull(_cursorIndexOfStatusText)) {
              _tmpStatusText = null;
            } else {
              _tmpStatusText = _cursor.getString(_cursorIndexOfStatusText);
            }
            final String _tmpAbout;
            if (_cursor.isNull(_cursorIndexOfAbout)) {
              _tmpAbout = null;
            } else {
              _tmpAbout = _cursor.getString(_cursorIndexOfAbout);
            }
            final long _tmpRegisteredAtMillis;
            _tmpRegisteredAtMillis = _cursor.getLong(_cursorIndexOfRegisteredAtMillis);
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final boolean _tmpAccountLocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAccountLocked);
            _tmpAccountLocked = _tmp_1 != 0;
            _item = new UserProfileEntity(_tmpUserId,_tmpFirstName,_tmpLastName,_tmpMiddleName,_tmpPhotoUri,_tmpPosition,_tmpDepartmentId,_tmpEmail,_tmpPhone,_tmpBirthDateMillis,_tmpStatusText,_tmpAbout,_tmpRegisteredAtMillis,_tmpRole,_tmpIsOnline,_tmpAccountLocked);
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
  public Object maxUserId(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(userId) FROM user_profiles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
