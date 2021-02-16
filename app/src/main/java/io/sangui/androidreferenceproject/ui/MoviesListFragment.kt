package io.sangui.androidreferenceproject.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    private val args: MoviesListFragmentArgs by navArgs()
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


        vm.insert(Movie(args.title, args.description, args.note))
        Toast.makeText(context, "Movie inserted!", Toast.LENGTH_SHORT).show()
        vm.update(Movie(args.title, args.description, args.note))
        Toast.makeText(context, "Movie updated!", Toast.LENGTH_SHORT).show()



        return view
    }

    private fun setUpRecyclerView() {
        adapter = MovieAdapter { clickedMovie ->
            val description = clickedMovie.description ?: ""
            val action = MoviesListFragmentDirections.actionMoviesListFragmentToAddEditMovieFragment(clickedMovie.title, description, clickedMovie.note)
            findNavController().navigate(action)
        }

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }

    private fun setUpListeners() {

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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (data != null) {
//            val title = data.getStringExtra(EXTRA_TITLE)
//            val description = data.getStringExtra(EXTRA_DESCRIPTION)
//            val priority = data.getIntExtra(EXTRA_NOTE, -1)
//
//            if (requestCode == ADD_MOVIE_REQUEST && resultCode == Activity.RESULT_OK) {
//                if (description != null && title != null) {
//                    vm.insert(Movie(title, description, priority))
//                    Toast.makeText(this, "Movie inserted!", Toast.LENGTH_SHORT).show()
//                }
//            } else if (requestCode == EDIT_MOVIE_REQUEST && resultCode == Activity.RESULT_OK) {
//                val id = data.getIntExtra(EXTRA_ID, -1)
//                if (id == -1) {
//                    Toast.makeText(this, "Movie couldn't be updated!", Toast.LENGTH_SHORT).show()
//                    return
//                }
//
//                if (title != null) {
//                    vm.update(Movie(title, description, priority, id))
//                    Toast.makeText(this, "Movie updated!", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Movie not saved!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}