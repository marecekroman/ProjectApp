package cz.utb.fai.projectapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.utb.fai.projectapp.model.MessageEntity

@Dao // Declare the interface as a Data Access Object (DAO)
interface MessageDao {
    @Insert // Annotation to insert a new row
    suspend fun insert(message: MessageEntity) // Function to insert a new message into the database

    @Query("SELECT * FROM messages ORDER BY timestamp ASC") // SQL query to select all messages ordered by timestamp
    fun getAllMessages(): LiveData<List<MessageEntity>> // Function to get all messages from the database

    @Query("DELETE FROM messages") // SQL query to delete all messages
    suspend fun deleteAllMessages() // Function to delete all messages from the database
}