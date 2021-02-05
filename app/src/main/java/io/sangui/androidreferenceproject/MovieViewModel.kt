package io.sangui.androidreferenceproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MovieViewModel(app: Application): AndroidViewModel(app) {
    private val repository = MovieRepository(app)

    fun insert(movie: Movie) {
        repository.insert(movie)
    }

    fun delete(movie: Movie) {
        repository.delete(movie)
    }
}