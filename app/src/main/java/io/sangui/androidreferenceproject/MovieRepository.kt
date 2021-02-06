package io.sangui.androidreferenceproject

import android.app.Application

class MovieRepository(application: Application) {

    private var database = MovieDatabase.getInstance(application)
    private var movieDao: MovieDao = database.movieDao()

    fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    fun delete(movie: Movie) {
        movieDao.delete(movie)
    }
}