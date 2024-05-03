package com.example.androidapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp.entity.Note
import com.example.androidapp.viewModel.NoteViewModel
import com.example.androidapp.viewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment(), DialogListener {

    private val viewModel: NotesViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = this.activity?.getSharedPreferences("NOTES_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreference?.getString("username","")

        noteViewModel = username?.let { NoteViewModel(it) }!!

        noteViewModel.noteList?.observe(viewLifecycleOwner,
            Observer { recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewNotes)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = NotesAdapter(it, noteViewModel, this) })

        view.findViewById<ImageButton>(R.id.addButton).setOnClickListener{
            showDialog(username, noteViewModel)
        }

        view.findViewById<TextView>(R.id.welcomeTextView).text = "Welcome, "+username

        view.findViewById<TextView>(R.id.logoutTextView).setOnClickListener {
            sharedPreference?.edit()?.remove("username")?.commit()

            val navigate = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(navigate)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreference = this.activity?.getSharedPreferences("NOTES_PREFERENCES", Context.MODE_PRIVATE)
        sharedPreference?.edit()?.clear()?.commit()
    }

    private fun showDialog(username: String?, noteViewModel: NoteViewModel?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_dialog)

        val body = dialog.findViewById(R.id.titleTextView) as TextView
        body.text = "Input Data"

        val yesBtn = dialog.findViewById(R.id.Button) as Button
        yesBtn.text = "Input"
        yesBtn.setOnClickListener {
            val title = dialog.findViewById<AppCompatEditText>(R.id.titleEditText).text.toString()
            val note = dialog.findViewById<AppCompatEditText>(R.id.noteEditText).text.toString()

            Log.d("tes", "$title $note")

            noteViewModel?.insertNote(Note(0, username, title,note))
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun editDialog(uid: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_dialog)

        val body = dialog.findViewById(R.id.titleTextView) as TextView
        body.text = "Edit Data"



        val yesBtn = dialog.findViewById(R.id.Button) as Button
        yesBtn.text = "Update"
        yesBtn.setOnClickListener {
            val titleResult = dialog.findViewById<AppCompatEditText>(R.id.titleEditText).text.toString()
            val noteResult = dialog.findViewById<AppCompatEditText>(R.id.noteEditText).text.toString()

            noteViewModel.updateNote(uid, titleResult, noteResult)
            dialog.dismiss()
        }

        dialog.show()
    }
}