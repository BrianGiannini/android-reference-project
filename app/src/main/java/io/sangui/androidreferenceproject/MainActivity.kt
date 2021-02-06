package io.sangui.androidreferenceproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sangui.androidreferenceproject.databinding.ActivityMainBinding

const val ADD_NOTE_REQUEST = 1
const val EDIT_NOTE_REQUEST = 2

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MovieViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setUpListeners()

        vm = ViewModelProvider(this).get(MovieViewModel::class.java)
        vm.getAllMovies().observe(this, {
            adapter.submitList(it)
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        adapter = MovieAdapter { clickedMovie ->
            val intent = Intent(this, AddEditMovieActivity::class.java)
            intent.putExtra(EXTRA_ID, clickedMovie.id)
            intent.putExtra(EXTRA_TITLE, clickedMovie.title)
            intent.putExtra(EXTRA_DESCRIPTION, clickedMovie.description)
            intent.putExtra(EXTRA_NOTE, clickedMovie.note)
            startActivityForResult(intent, EDIT_NOTE_REQUEST)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setUpListeners() {
        binding.buttonAddMovie.setOnClickListener {
            val intent = Intent(this, AddEditMovieActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
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
                val note = adapter.getMovieAt(viewHolder.adapterPosition)
                vm.delete(note)
            }

        }).attachToRecyclerView(binding.recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val title = data.getStringExtra(EXTRA_TITLE)
            val description = data.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(EXTRA_NOTE, -1)

            if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
                if (description != null && title != null) {
                    vm.insert(Movie(title, description, priority))
                    Toast.makeText(this, "Note inserted!", Toast.LENGTH_SHORT).show()
                }
            } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
                val id = data.getIntExtra(EXTRA_ID, -1)
                if (id == -1) {
                    Toast.makeText(this, "Note couldn't be updated!", Toast.LENGTH_SHORT).show()
                    return
                }

                if (description != null && title != null) {
                    vm.update(Movie(title, description, priority, id))
                    Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}