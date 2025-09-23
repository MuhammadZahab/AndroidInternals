package com.zahab.androidinternals.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [DictionaryEntity::class]
)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract val dao: DictionaryDao
}