package com.example.androidapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp.entity.Note
import com.example.androidapp.viewModel.NoteViewModel

class NotesAdapter (private val mNote: List<Note>, private val noteViewModel: NoteViewModel, private val dialogListener: DialogListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
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

        val itemsViewModel = mNote[position]

        holder.title.text = itemsViewModel.title
        holder.note.text = itemsViewModel.note

        holder.editButton.setOnClickListener{
            dialogListener.editDialog(itemsViewModel.uid)
            Log.d("button clicked", "edit button $itemsViewModel.title, $itemsViewModel.note, $position")
        }
        holder.deleteButton.setOnClickListener{
            noteViewModel.deleteByUid(itemsViewModel.uid)
            Log.d("button clicked", "delete button $itemsViewModel.title, $itemsViewModel.note, $position")
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        Log.d("size", mNote.toString())
        Log.d("size", mNote.size.toString())
        return mNote.size

    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.titleTextView)
        var note: TextView = itemView.findViewById(R.id.noteTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
}