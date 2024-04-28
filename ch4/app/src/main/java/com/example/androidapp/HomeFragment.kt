package com.example.androidapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private val viewModel: NotesViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

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

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewNotes)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NotesAdapter(viewModel)

//        viewModel.readNotes.observe(viewLifecycleOwner, Observer { list ->
//            recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewNotes)
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = NotesAdapter(list, viewModel)
//        })


    }
}