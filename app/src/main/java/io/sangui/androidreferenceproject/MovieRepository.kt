package io.sangui.androidreferenceproject

import android.app.Application
import androidx.lifecycle.LiveData

class MovieRepository(application: Application) {

    private var database = MovieDatabase.getInstance(application)
    private var movieDao: MovieDao = database.movieDao()
    private var allMovies: LiveData<List<Movie>> = movieDao.getAllMovies()

    fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    fun update(note: Movie) {
        movieDao.update(note)
    }

    fun delete(movie: Movie) {
        movieDao.delete(movie)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return allMovies
    }
}