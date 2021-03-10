package io.sangui.androidreferenceproject.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sangui.androidreferenceproject.Movie
import io.sangui.androidreferenceproject.MovieAdapter
import io.sangui.androidreferenceproject.R
import io.sangui.androidreferenceproject.databinding.MoviesListFragmentBinding

class MoviesListFragment : Fragment(R.layout.movies_list_fragment) {

    private lateinit var vm: MovieViewModel
    private lateinit var binding: MoviesListFragmentBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesListFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        setUpRecyclerView()
        setUpListeners()

        vm = ViewModelProvider(this).get(MovieViewModel::class.java)
        vm.getAllMovies().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        return view
    }

    private fun setUpRecyclerView() {
        adapter = MovieAdapter { clickedMovie ->
            moveToNextScreen(clickedMovie)
        }

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }

    private fun setUpListeners() {
        binding.buttonAddMovie.setOnClickListener {
            val action = MoviesListFragmentDirections.actionMoviesListFragmentToAddEditMovieFragment("", "", 5,)
            findNavController().navigate(action)
        }

        // swipe listener
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movie = adapter.getMovieAt(viewHolder.adapterPosition)
                vm.delete(movie)
            }

        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun moveToNextScreen(movie: Movie) {
        val action =
            MoviesListFragmentDirections.actionMoviesListFragmentToAddEditMovieFragment(
                movie.title,
                movie.description ?: "",
                movie.note,
                movie.id ?: -1
            )
        findNavController().navigate(action)
    }

}