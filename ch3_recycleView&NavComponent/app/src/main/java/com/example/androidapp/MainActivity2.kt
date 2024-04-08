package com.example.androidapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity(), onClickListener {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = getRandomWords(intent.getIntExtra("position", 0))

        val itemAdapter= CustomAdapter (data, this)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        val word = intent.getStringExtra("value")
        title = "Words That Start With $word"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_button -> {
                if (!item.isChecked){
                    recyclerView.layoutManager = GridLayoutManager(this,3)
                    item.setIcon(R.drawable.baseline_list_24)
                    item.setChecked(true)
                }else{
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    item.setIcon(R.drawable.baseline_grid_view_24)
                    item.setChecked(false)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onItemClick(position: Int, value: String) {
        val uri = Uri.parse("https://www.google.com/search?q=$value")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
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