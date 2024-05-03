package com.example.androidapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel: ViewModel() {
    private var notes = MutableLiveData<ArrayList<noteDTO>>(arrayListOf(noteDTO("title","notes one")))
    val readNotes : LiveData<ArrayList<noteDTO>> get() = notes

    fun addNote(title: String, note: String){
        Log.d("viewModel", "addNote")
        var newNotes = this.notes.value
        if (newNotes == null) {
            newNotes = arrayListOf<noteDTO>()
        }
        newNotes.add(noteDTO(title,note))
        this.notes.postValue(newNotes)
        Log.d("viewModel", this.notes.value?.size.toString())
    }
}