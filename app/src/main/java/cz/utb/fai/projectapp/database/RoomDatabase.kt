package cz.utb.fai.projectapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.utb.fai.projectapp.messagedao.MessageDao
import cz.utb.fai.projectapp.model.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
