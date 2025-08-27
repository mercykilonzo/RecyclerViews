package com.classwork.recyclerviews.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.classwork.recyclerviews.R

class MainActivity : AppCompatActivity() {
    lateinit var rvNames: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume(){
        super.onResume()
        rvNames = findViewById(R.id.rvNames)
        val names = listOf("Anne", "Thomas", "Andrew", "John","Emebet", "Eden", "Eyob", "Semhal"
            , "Teklay", "Senait", "Elias", "Mercy", "Johnatan", "Angela", "Sarah", "Daniella")
        val namesAdapter = NamesRvAdapter(names)
        rvNames.layoutManager = GridLayoutManager(this, 4)
        rvNames.adapter = namesAdapter

    }


}