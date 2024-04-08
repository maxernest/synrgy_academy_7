package com.example.androidapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.URL
import java.util.Random
import kotlin.properties.Delegates


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SecondFragment : Fragment(), onClickListener {

    private lateinit var recyclerView: RecyclerView
    private var index: Int = 0
    private val args : SecondFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val word = args.value
        activity?.title = "Words That Start With $word";
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        index = args.position

        val data = getRandomWords(index)

        val itemAdapter= CustomAdapter (data, this)

        recyclerView = view.findViewById(R.id.recyclerview1)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = itemAdapter
    }

    override fun onItemClick(position: Int, value: String) {
        val uri = Uri.parse("https://www.google.com/search?q=$value")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.menu_button -> {
                if (!item.isChecked){
                    recyclerView.layoutManager = GridLayoutManager(context,2)
                    item.setIcon(R.drawable.baseline_list_24)
                    item.setChecked(true)
                }else{
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    item.setIcon(R.drawable.baseline_grid_view_24)
                    item.setChecked(false)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getRandomWords(index:Int): List<String>{
        val dictionaries = listOf(
            listOf("appear", "antsy", "apple"),
            listOf("bounce", "bright", "button"),
            listOf("chair", "clever", "cloud"),
            listOf("dance", "daring", "delight"),
            listOf("echo", "eager", "elegant"),
            listOf("flamingo", "flicker", "floor"),
            listOf("giggle", "gorgeous", "grass"),
            listOf("hammer", "hat", "happy"),
            listOf("imagine", "inch", "island"),
            listOf("juggle", "juice", "jam"),
            listOf("kite", "knowledge", "key"),
            listOf("lamp", "laugh", "lunch"),
            listOf("marble", "melodic", "map"),
            listOf("nest", "nervous", "night"),
            listOf("octopus", "ocean", "offer"),
            listOf("paint", "piano", "perfect"),
            listOf("quilt", "quiet", "queen"),
            listOf("rain", "rainbow", "race"),
            listOf("smile", "snack", "sleep"),
            listOf("tent", "terrible", "tasty"),
            listOf("umbrella", "unique", "unwind"),
            listOf("vase", "violin", "visit"),
            listOf("window", "whisper", "wild"),
            listOf("xylophone", "xylograph", "xylography"), // Less common "x" words
            listOf("yacht", "yawn", "yield"),
            listOf("zebra", "zealous", "zigzag")
        )

        return dictionaries.get(index)
    }
}