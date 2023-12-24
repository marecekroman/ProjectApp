package cz.utb.fai.projectapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Make sure it's auto-generated
    val text: String,
    val timestamp: Long,
    val isSender: Boolean
)