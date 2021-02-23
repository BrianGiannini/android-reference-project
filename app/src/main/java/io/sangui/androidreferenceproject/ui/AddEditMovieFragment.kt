package io.sangui.androidreferenceproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.sangui.androidreferenceproject.Movie
import io.sangui.androidreferenceproject.R
import io.sangui.androidreferenceproject.databinding.AddEditMovieFragmentBinding


class AddEditMovieFragment : Fragment(R.layout.add_edit_movie_fragment) {

    private val args: AddEditMovieFragmentArgs by navArgs()
    private lateinit var binding: AddEditMovieFragmentBinding
    private lateinit var vm: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEditMovieFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        vm = ViewModelProvider(this).get(MovieViewModel::class.java)

        with(binding) {
            numberPickerPriority.minValue = 1
            numberPickerPriority.maxValue = 10
            buttonSaveEditMovie.setOnClickListener {
                saveMovie()
            }
            etTitle.setText(args.title)
            etDesc.setText(args.description)
            numberPickerPriority.value = args.note
        }

        return view
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString()
        val description = binding.etDesc.text.toString()
        val note = binding.numberPickerPriority.value

        if (title.isEmpty()) {
            Toast.makeText(context, "please insert title and description", Toast.LENGTH_SHORT).show()
        } else {
            vm.insert(Movie(title, description, note))
            Toast.makeText(context, "Movie inserted!", Toast.LENGTH_SHORT).show()
            val action = AddEditMovieFragmentDirections.actionAddEditMovieFragmentToMoviesListFragment("", "", 5)
            findNavController().navigate(action)
        }
    }

}