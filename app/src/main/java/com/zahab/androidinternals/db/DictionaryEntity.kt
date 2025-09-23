package com.zahab.androidinternals.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DictionaryEntity(
    val word: String,
    val definition: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)