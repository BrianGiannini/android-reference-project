package io.sangui.androidreferenceproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Movie(
    val title: String,
    val note: Int,
    @PrimaryKey(autoGenerate = false) val id: Int? = null

)