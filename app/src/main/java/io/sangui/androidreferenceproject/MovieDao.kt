package io.sangui.androidreferenceproject

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)
}