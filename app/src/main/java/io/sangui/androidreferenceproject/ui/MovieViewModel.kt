package io.sangui.androidreferenceproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.sangui.androidreferenceproject.Movie
import io.sangui.androidreferenceproject.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = MovieRepository(app)
    private val allMovies = repository.getAllMovies()


    fun insert(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return allMovies
    }

    fun update(movie: Movie) {
        viewModelScope.launch {
            repository.update(movie)
        }
    }
}