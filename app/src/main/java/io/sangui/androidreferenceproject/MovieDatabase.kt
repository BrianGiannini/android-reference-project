package io.sangui.androidreferenceproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: MovieDatabase? = null

        @Synchronized
        @NotNull
        fun getInstance(context: Context): MovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, MovieDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val scope = CoroutineScope(Dispatchers.Default)
                scope.launch {
                    populateDatabase(instance)
                }
            }
        }

        private fun populateDatabase(db: MovieDatabase?)  {
            if (db != null) {
                val movieDao = db.movieDao()
                    movieDao.insert(Movie("Matrix 1", "SF movie", 10))
                    movieDao.insert(Movie("Sharknado", "Nanar movie", 1))
            }
        }
    }
}