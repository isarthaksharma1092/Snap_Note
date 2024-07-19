package com.isarthaksharma.snapnote

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.isarthaksharma.snapnote.databinding.ActivityAddNotesBinding

class addNotes : AppCompatActivity() {
    private lateinit var bindingAddNote:ActivityAddNotesBinding
    private lateinit var db:NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // view binding
        bindingAddNote = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(bindingAddNote.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            //code
            gradientText()

            db = NoteDatabaseHelper(this)
            bindingAddNote.saveBtn.setOnClickListener {
                val title = bindingAddNote.newNoteEdit.text.toString()
                val description = bindingAddNote.newNoteDescribe.text.toString()

                if(title.isBlank()){
                    bindingAddNote.newNoteEdit.hint = "* Requied"
                    Toast.makeText(this,"Note can't be empty :/",Toast.LENGTH_SHORT).show()
                }else{
                    bindingAddNote.newNoteEdit.hint = "Enter Note"
                    val note = noteData(0,title,description)
                    db.insertNote(note)
                    finish()
                    Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }

            }
            bindingAddNote.goBackBtn.setOnClickListener {
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this,"Nothing was Saved",Toast.LENGTH_SHORT).show()
                finish()
            }
            insets
        }
    }

    private fun gradientText() {
        val paint = bindingAddNote.addNoteText.paint
        val width = paint.measureText(bindingAddNote.addNoteText.text.toString())
        paint.shader = LinearGradient(
            0f,0f,width,bindingAddNote.addNoteText.textSize, intArrayOf(
                Color.parseColor("#ffa48d"),
                Color.parseColor("#fe6387"),
                Color.parseColor("#fd3b84")
            ),null, Shader.TileMode.REPEAT
        )
    }
}