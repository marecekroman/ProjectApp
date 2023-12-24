package cz.utb.fai.projectapp.messagedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.utb.fai.projectapp.model.MessageEntity

@Dao
interface MessageDao {
    @Insert
    suspend fun insert(message: MessageEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): LiveData<List<MessageEntity>>
}