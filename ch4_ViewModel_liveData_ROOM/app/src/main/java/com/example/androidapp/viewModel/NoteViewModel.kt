package com.example.androidapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.MainApplication.Companion.noteDatabase
import com.example.androidapp.NoteDatabase
import com.example.androidapp.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (userName:String) : ViewModel() {

    private val noteDao = noteDatabase.noteDao()

    val noteList : LiveData<List<Note>>? = noteDao.findByName(userName)

    fun insertNote(note: Note){
        viewModelScope.launch { noteDao.insertAll(note) }
    }

    fun updateNote(uid: Int, title: String, note: String){
        viewModelScope.launch { noteDao.updateNote(uid, title, note) }
    }

    fun deleteByUid(uid:Int){
        viewModelScope.launch { noteDao.deleteByUid(uid) }
    }
}