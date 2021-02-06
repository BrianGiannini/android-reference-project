package io.sangui.androidreferenceproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
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

    fun update(note: Movie) {
        viewModelScope.launch {
            repository.update(note)
        }
    }
}