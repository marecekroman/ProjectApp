package cz.utb.fai.projectapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// This annotation marks the class as a SQLite table with the name 'messages'
@Entity(tableName = "messages")

// This is a data class named 'MessageEntity'. Data classes in Kotlin are used to hold data
  data class MessageEntity(

  // This annotation marks 'id' as the primary key of the table and it will be auto-generated
    @PrimaryKey(autoGenerate = true)

    // This is the 'id' property of the 'MessageEntity'. It is of type 'Int' and its default value is 0
    val id: Int = 0,

  // This is the 'text' property of the 'MessageEntity'. It is of type 'String'
    val text: String,

  // This is the 'timestamp' property of the 'MessageEntity'. It is of type 'Long'
    val timestamp: Long,

  // This is the 'isSender' property of the 'MessageEntity'. It is of type 'Boolean'
    val isSender: Boolean
)