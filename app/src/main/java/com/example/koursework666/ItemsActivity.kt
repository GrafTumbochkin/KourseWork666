package com.example.koursework666

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity() {


    private lateinit var itemsAdapter: ItemsAdapter
    private var items = arrayListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        val items = arrayListOf<Item>()



        items.add(Item(1,"divan", "Диван", "вввв", "йцуцйыв", 5, 1000))
        items.add(Item(2,"molot", "Молот", "вфффф", "ыфссув", 4,150))
        items.add(Item(3,"gvozdi", "Гвозди", "ыффффф", "вцйвыфсч", 100,200))

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(items, this)

        items.removeAll { it.count == 0 }
        itemsList.adapter?.notifyDataSetChanged()
    }
}