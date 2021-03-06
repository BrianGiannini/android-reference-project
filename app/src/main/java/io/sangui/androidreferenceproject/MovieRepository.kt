package io.sangui.androidreferenceproject

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(application: Application) {

    private var database = MovieDatabase.getInstance(application)
    private var movieDao: MovieDao = database.movieDao()

    suspend fun insert(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.insert(movie)
    }

    suspend fun update(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.update(movie)
    }

    suspend fun delete(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.delete(movie)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }
}