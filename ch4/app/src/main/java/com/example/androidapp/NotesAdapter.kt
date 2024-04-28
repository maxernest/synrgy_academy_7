package com.example.androidapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter (private val notesViewModel: NotesViewModel) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_note, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = notesViewModel.readNotes.value?.get(position)

        if (itemsViewModel != null) {
            holder.title.text = itemsViewModel.title
        }
        if (itemsViewModel != null) {
            holder.note.text = itemsViewModel.note
        }

        holder.editButton.setOnClickListener{
            notesViewModel.addNote("title two", "note two")
            Log.d("button clicked", "edit button $itemsViewModel.title, $itemsViewModel.note, $position")
        }
        holder.deleteButton.setOnClickListener{
            Log.d("button clicked", "delete button $itemsViewModel.title, $itemsViewModel.note, $position")
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        val totalItems = notesViewModel.readNotes.value?.size
        return totalItems ?: 0

    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.titleTextView)
        var note: TextView = itemView.findViewById(R.id.noteTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
}