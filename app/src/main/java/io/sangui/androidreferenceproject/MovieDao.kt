package io.sangui.androidreferenceproject

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("select * from movie_table order by note desc")
    fun getAllMovies(): LiveData<List<Movie>>
}