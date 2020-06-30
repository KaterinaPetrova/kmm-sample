package com.example.cheerup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epetrova.kmm_sample.GifService
import com.epetrova.kmm_sample.GifType

class MainActivity : AppCompatActivity() {

    private val gifService = GifService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Cheer up!"
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        val gifTypesAdapter = GifTypesAdapter(gifService.availableTypes)
        recyclerView.adapter = gifTypesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        findViewById<Button>(R.id.goToGifList).setOnClickListener {
            startActivity(
                Intent(
                    this, GifListActivity::class.java
                ).putExtra(
                    selectedGifTypesIndexesKey,
                    ArrayList(gifTypesAdapter.getSelectedGifTypesPositions())
                )
            )
        }
    }
}

class GifTypesAdapter(
    private val gifTypes: List<GifType>
) : RecyclerView.Adapter<GifTypeViewHolder>() {

    private val selectedItems = mutableSetOf<Int>()

    fun getSelectedGifTypesPositions(): Set<Int> {
        return selectedItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifTypeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.git_type_view, parent, false)
        return GifTypeViewHolder(view)
    }

    override fun getItemCount(): Int = gifTypes.size

    override fun onBindViewHolder(holder: GifTypeViewHolder, position: Int) {
        val gifType = gifTypes[position]
        holder.itemView.findViewById<TextView>(R.id.gifTypeTitle).text = gifType.title
        holder.itemView.setBackgroundColor(
            Color.rgb(
                gifType.color.red,
                gifType.color.green,
                gifType.color.blue
            )
        )
        val selectedView = holder.itemView.findViewById<View>(R.id.selected)
        holder.itemView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
                selectedView.visibility = View.GONE
            } else {
                selectedItems.add(position)
                selectedView.visibility = View.VISIBLE
            }
        }
    }

}

class GifTypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}