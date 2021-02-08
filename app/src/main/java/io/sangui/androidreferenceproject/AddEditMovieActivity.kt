package io.sangui.androidreferenceproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.sangui.androidreferenceproject.databinding.ActivityAddEditMovieBinding

const val EXTRA_ID = "EXTRA_ID"
const val EXTRA_TITLE = "EXTRA_TITLE"
const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
const val EXTRA_NOTE = "EXTRA_NOTE"

class AddEditMovieActivity : AppCompatActivity() {

    private lateinit var mode: Mode
    private var movieId: Int = -1

    private lateinit var binding: ActivityAddEditMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberPickerPriority.minValue = 1
        binding.numberPickerPriority.maxValue = 10

        binding.buttonSaveEditMovie.setOnClickListener {
            saveMovie()
        }

        movieId = intent.getIntExtra(EXTRA_ID, -1)
        mode = if (movieId == -1) Mode.EditMovie
        else Mode.EditMovie

        when (mode) {
            Mode.AddMovie -> title = "Add Movie"
            Mode.EditMovie -> {
                title = "Edit Movie"
                binding.etTitle.setText(intent.getStringExtra(EXTRA_TITLE))
                binding.etDesc.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
                binding.numberPickerPriority.value = intent.getIntExtra(EXTRA_NOTE, -1)
            }
        }
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val priority = binding.numberPickerPriority.value

        if (title.isEmpty()) {
            Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()

        if (movieId != -1)
            data.putExtra(EXTRA_ID, movieId)
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, desc)
        data.putExtra(EXTRA_NOTE, priority)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private sealed class Mode {
        object AddMovie : Mode()
        object EditMovie : Mode()
    }

}