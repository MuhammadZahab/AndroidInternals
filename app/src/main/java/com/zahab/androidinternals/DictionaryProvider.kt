package com.zahab.androidinternals

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.zahab.androidinternals.db.DictionaryDao
import com.zahab.androidinternals.db.DictionaryDatabase
import com.zahab.androidinternals.db.DictionaryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import kotlin.getValue

class DictionaryProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.zahab.androidinternals"
        private const val WORDS_CODE = 100
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "words", 100)
        }
    }

    lateinit var dictionaryDao: DictionaryDao

    val appScope by lazy {
        (context?.applicationContext as? MyDictionaryApp)?.applicationScope!!
    }


    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext ?: return false

        dictionaryDao = Room.databaseBuilder(
            context = appContext,
            klass = DictionaryDatabase::class.java,
            name = "dictionary.db"
        ).build().dao


        appScope.launch {
            prepopulateDB()
        }

        return true
    }

    private suspend fun parseCSVFile() = withContext(Dispatchers.IO) {

        try {
            context?.applicationContext!!.assets.open("english-dict.csv").use { inputStream ->

                val records = CSVFormat.DEFAULT.parse(inputStream.bufferedReader())
                records.toList().drop(1).mapNotNull { record ->
                    val word = record.get(0)
                    val definition = record.get(2)

                    DictionaryEntity(
                        word = word ?: return@mapNotNull null,
                        definition = definition ?: return@mapNotNull null
                    )
                }

            }

        } catch (e: Exception) {
            appScope.ensureActive()
            e.printStackTrace()
            emptyList<DictionaryEntity>()

        }

    }

    private suspend fun prepopulateDB() {
        if (dictionaryDao.getCount() == 0L) {
            val words = parseCSVFile()
            dictionaryDao.insertAll(words)
        }
    }

    override fun delete(
        p0: Uri,
        p1: String?,
        p2: Array<out String?>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        return "vnd.android.cursor.dir/vnd.$AUTHORITY"
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String?>?,
        selection: String?,
        selectionArgs: Array<out String?>?,
        sortOrder: String?
    ): Cursor? {

        return when (uriMatcher.match(uri)) {
            WORDS_CODE -> {
                selectionArgs?.getOrNull(0)?.let { query ->
                    dictionaryDao.findByWord(word = query)
                } ?: dictionaryDao.getAll()
            }

            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun update(
        p0: Uri,
        p1: ContentValues?,
        p2: String?,
        p3: Array<out String?>?
    ): Int {
        TODO("Not yet implemented")
    }
}