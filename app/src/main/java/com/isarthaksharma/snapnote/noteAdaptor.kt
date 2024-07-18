package com.isarthaksharma.snapnote

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class noteAdaptor(private var notes:List<noteData>,context: Context)
    : RecyclerView.Adapter<noteAdaptor.noteViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return noteViewHolder(view)
    }

    override fun getItemCount(): Int {
        //return the list size
        return notes.size
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleText.text = note.title
        holder.descriptionText.text = note.description
        holder.descriptionText.maxLines = 5
        holder.descriptionText.ellipsize = TextUtils.TruncateAt.END
    }


    class noteViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val titleText = itemView.findViewById<TextView>(R.id.NoteTitle)
        val descriptionText = itemView.findViewById<TextView>(R.id.NoteDescription)
    }

/***********************************************************************************************************
    fun deleteNote(holder: noteViewHolder,position: Int){
        val note = notes[position]
        holder.deleteBtn.setOnClickListener {
            db.deleteNote(note.id)
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_LONG).show()
            refreshData(db.getAllNotes())
        }
    }

    fun updateNote(holder: noteViewHolder,position: Int){
        val note = notes[position]
        holder.updateBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context,noteUpdate::class.java).apply {
                putExtra("NOTE_ID",note.id)
                putExtra("TITLE",note.title)
                putExtra("DESCRIPTION",note.description)
            }
            holder.itemView.context.startActivity(intent)

        }
    }
*/
    fun getNoteAtPosition(position: Int): noteData {
        return notes[position]
    }

    fun refreshData(newNotes:List<noteData>){
        notes = newNotes
        notifyDataSetChanged()
    }

}