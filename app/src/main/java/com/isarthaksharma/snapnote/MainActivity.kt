package com.isarthaksharma.snapnote

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isarthaksharma.snapnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdaptor: noteAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            //Code
            gradientText()

            //setting up recyclerView
            db = NoteDatabaseHelper(this)
            notesAdaptor = noteAdaptor(db.getAllNotes(),this)
            notesAdaptor.refreshData(db.getAllNotes())
            binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.noteRecyclerView.adapter = notesAdaptor
            //refeshing the screen

            //adding note to recycler view
            binding.addNoteBtn.setOnClickListener{
                startActivity(Intent(this,addNotes::class.java))
                finish()
            }

            //swipeGesture
            val swipeGesture = object:swipeGesture(this){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    when(direction){
                        ItemTouchHelper.LEFT->{
                            val note = notesAdaptor.getNoteAtPosition(position)
                            db.deleteNote(note.id)
                            notesAdaptor.refreshData(db.getAllNotes())
                            Toast.makeText(this@MainActivity,"-.- Deleted -.-",Toast.LENGTH_LONG).show()
                        }
                        ItemTouchHelper.RIGHT->{
                            val note = notesAdaptor.getNoteAtPosition(position)
                            val intent = Intent(this@MainActivity, noteUpdate::class.java).apply {
                                putExtra("NOTE_ID", note.id)
                                putExtra("TITLE", note.title)
                                putExtra("DESCRIPTION", note.description)
                            }
                            startActivity(intent)
                            notesAdaptor.refreshData(db.getAllNotes())
                        }
                    }
                    notesAdaptor.notifyItemChanged(position)
                }
            }
            val touchHelper = ItemTouchHelper(swipeGesture)
            touchHelper.attachToRecyclerView(binding.noteRecyclerView)
            insets
        }
    }


    private fun gradientText() {
        val paint = binding.yourNotesText.paint
        val width = paint.measureText(binding.yourNotesText.text.toString())
        paint.shader = LinearGradient(
            0f,0f,width,binding.yourNotesText.textSize, intArrayOf(
                Color.parseColor("#ffa48d"),
                Color.parseColor("#fd3b84"),
                Color.parseColor("#fe6387")
            ),null, Shader.TileMode.REPEAT
        )
    }


}