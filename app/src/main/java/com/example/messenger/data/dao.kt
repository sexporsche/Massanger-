package com.example.messenger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.messenger.data.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(msg: MessageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(msgs: List<MessageEntity>)

    // Используем snake_case (как в серверной схеме/schema.sql)
    @Query("SELECT * FROM messages WHERE (recipient_id = :uid OR sender_id = :uid) ORDER BY timestamp ASC")
    fun getForUserFlow(uid: Int): Flow<List<MessageEntity>>

    /** Только переписка между текущим пользователем и выбранным контактом */
    @Query(
        """SELECT * FROM messages WHERE 
        (sender_id = :me AND recipient_id = :peer) OR 
        (sender_id = :peer AND recipient_id = :me) 
        ORDER BY timestamp ASC"""
    )
    fun getConversationFlow(me: Int, peer: Int): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE group_id = :groupKey ORDER BY timestamp ASC")
    fun observeGroupMessages(groupKey: Int): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAllFlow(): Flow<List<MessageEntity>>
}