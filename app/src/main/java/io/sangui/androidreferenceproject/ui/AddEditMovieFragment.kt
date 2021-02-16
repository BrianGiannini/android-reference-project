package io.sangui.androidreferenceproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.sangui.androidreferenceproject.R
import io.sangui.androidreferenceproject.databinding.AddEditMovieFragmentBinding


class AddEditMovieFragment : Fragment(R.layout.add_edit_movie_fragment) {

    private val args: AddEditMovieFragment by navArgs()

    private lateinit var binding: AddEditMovieFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEditMovieFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        with(binding) {
            numberPickerPriority.minValue = 1
            numberPickerPriority.maxValue = 10
            buttonSaveEditMovie.setOnClickListener {
                saveMovie()
            }
        }
        return view
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val note = binding.numberPickerPriority.value

        if (title.isEmpty()) {
            Toast.makeText(context, "please insert title and description", Toast.LENGTH_SHORT).show()
        }

        val action = AddEditMovieFragmentDirections.actionAddEditMovieFragmentToMoviesListFragment(title, desc, note)
        findNavController().navigate(action)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        when (args.mode) {
//            Mode.AddMovie -> title = "Add Movie"
//            Mode.EditMovie -> {
//                title = "Edit Movie"
//                with(binding) {
////                    etTitle.setText(args.title)
////                    etDesc.setText(args.description)
////                    numberPickerPriority.value = args.value
//                }
//            }
//        }
    }

}