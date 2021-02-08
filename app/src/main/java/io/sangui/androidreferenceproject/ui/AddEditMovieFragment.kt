package io.sangui.androidreferenceproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.sangui.androidreferenceproject.databinding.AddEditMovieFragmentBinding

const val EXTRA_ID = "EXTRA_ID"
const val EXTRA_TITLE = "EXTRA_TITLE"
const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
const val EXTRA_NOTE = "EXTRA_NOTE"

class AddEditMovieFragment : Fragment() {

    private lateinit var mode: Mode
    private var movieId: Int = -1

    private lateinit var binding: AddEditMovieFragmentBinding

    companion object {
        fun newInstance() = AddEditMovieFragment()
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        movieId = intent.getIntExtra(EXTRA_ID, -1)
//        mode = if (movieId == -1) Mode.EditMovie
//        else Mode.EditMovie

//        when (mode) {
//            Mode.AddMovie -> title = "Add Movie"
//            Mode.EditMovie -> {
//                title = "Edit Movie"
//                with(binding) {
//                    etTitle.setText(intent.getStringExtra(EXTRA_TITLE))
//                    etDesc.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
//                    numberPickerPriority.value = intent.getIntExtra(EXTRA_NOTE, -1)
//                }
//            }
//        }
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val priority = binding.numberPickerPriority.value

//        if (title.isEmpty()) {
//            Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val data = Intent()
//
//        if (movieId != -1)
//            data.putExtra(EXTRA_ID, movieId)
//        data.putExtra(EXTRA_TITLE, title)
//        data.putExtra(EXTRA_DESCRIPTION, desc)
//        data.putExtra(EXTRA_NOTE, priority)
//
//        setResult(Activity.RESULT_OK, data)
//        finish()
    }

    private sealed class Mode {
        object AddMovie : Mode()
        object EditMovie : Mode()
    }

}