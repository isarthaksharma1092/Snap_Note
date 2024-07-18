package com.isarthaksharma.snapnote

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.isarthaksharma.snapnote.databinding.ActivityNoteUpdateBinding

class noteUpdate : AppCompatActivity() {
    private lateinit var updateBinding:ActivityNoteUpdateBinding
    private lateinit var db:NoteDatabaseHelper
    private var noteId:Int  = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        updateBinding = ActivityNoteUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            db = NoteDatabaseHelper(this)
            noteId = intent.getIntExtra("NOTE_ID",-1)
            val noteTitle = intent.getStringExtra("TITLE")
            val noteDescription = intent.getStringExtra("DESCRIPTION")
            if(noteId == -1){
                finish()
            }

            val note = db.getNoteByID(noteId)
            updateBinding.oldNoteEdit.setText(noteTitle)
            updateBinding.oldNoteDescribe.setText(noteDescription)
            updateBinding.updateSaveBtn.setOnClickListener {
                val newTitle = updateBinding.oldNoteEdit.text.toString()
                val newDescribe = updateBinding.oldNoteDescribe.text.toString()
                val updatedNote = noteData(noteId, newTitle,newDescribe)
                db.updateNote(updatedNote)

                Toast.makeText(this,"Updated :)",Toast.LENGTH_LONG).show()
                finish()
            }
            updateBinding.goBackEditBtn.setOnClickListener {
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this,"Nothing was changed",Toast.LENGTH_LONG).show()
                finish()
            }

            insets
        }
    }
}