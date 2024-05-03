package com.example.androidapp

import android.app.Application

class MainApplication: Application() {
    companion object{
        lateinit var noteDatabase: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()

        noteDatabase = NoteDatabase.getDatabase(applicationContext)
    }
}