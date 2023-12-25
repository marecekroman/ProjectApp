package cz.utb.fai.projectapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.utb.fai.projectapp.dao.MessageDao
import cz.utb.fai.projectapp.model.MessageEntity

// This annotation defines the database, with MessageEntity as the entity and version 1
@Database(entities = [MessageEntity::class], version = 1)

// This is an abstract class named AppDatabase that extends RoomDatabase
abstract class AppDatabase : RoomDatabase() {

    // This is an abstract function that returns an instance of MessageDao
    abstract fun messageDao(): MessageDao
}
