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

class AddEditMovieActivity : AppCompatActivity(){

    private lateinit var mode: Mode
    private var noteId: Int = -1

    private lateinit var binding: ActivityAddEditMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberPickerPriority.minValue = 1
        binding.numberPickerPriority.maxValue = 10

        binding.buttonSaveEditNote.setOnClickListener {
            saveMovie()
        }
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val priority = binding.numberPickerPriority.value

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        // only if note ID was provided i.e. we are editing
        if (noteId != -1)
            data.putExtra(EXTRA_ID, noteId)
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