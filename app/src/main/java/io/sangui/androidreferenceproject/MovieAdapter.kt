package io.sangui.androidreferenceproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val onItemClickListener: (Movie) -> Unit) :
    ListAdapter<Movie, MovieAdapter.MovieHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent,
            false
        )
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        with(getItem(position)) {
            holder.movieTitle.text = title
            holder.movieDescription.text = description
            holder.movieNote.text = note.toString()
        }
    }

    inner class MovieHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val movieTitle: TextView = itemView.findViewById(R.id.movie_title_text)
        val movieDescription: TextView = itemView.findViewById(R.id.movie_description_text)
        val movieNote: TextView = itemView.findViewById(R.id.movie_note_text)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }

    }


}

private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.title == newItem.title
                && oldItem.description == newItem.description
                && oldItem.note == newItem.note
}