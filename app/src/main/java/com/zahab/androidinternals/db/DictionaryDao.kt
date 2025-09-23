package com.zahab.androidinternals.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DictionaryDao {

    @Upsert
    suspend fun insertAll(words: List<DictionaryEntity>)

    @Query("SELECT count(*) FROM dictionaryentity")
    suspend fun getCount(): Long

    @Query("SELECT * FROM DICTIONARYENTITY")
    fun getAll(): Cursor

    @Query("SELECT * FROM dictionaryentity where lower(word) = lower(:word)")
    fun findByWord(word: String): Cursor

}