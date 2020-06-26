package com.example.cheerup

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jetbrains.handson.mpp.mobile.Gif
import com.jetbrains.handson.mpp.mobile.GifService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

const val selectedGifTypesIndexesKey = "selectedGifTypesIndexes"

class GifListActivity : AppCompatActivity() {
    private val gifService = GifService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif_list)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        val indexes = intent.getIntegerArrayListExtra(selectedGifTypesIndexesKey)
        if (indexes != null) {
            val types =
                gifService.availableTypes.filterIndexed { index, _ -> indexes.contains(index) }
            MainScope().launch {
                val gifsAdapter = GifAdapter(gifService.getGifs(types), point.x)
                recyclerView.adapter = gifsAdapter
            }
            title = types.joinToString(" + ") { it.title }
        }
    }
}

class GifAdapter(
    private val gifs: List<Gif>,
    private val screenWidth: Int
) : RecyclerView.Adapter<GifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_view, parent, false)
        return GifViewHolder(view)
    }

    override fun getItemCount(): Int = gifs.size

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifImage = gifs[position].images.previewImage
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        val ratio = screenWidth / gifImage.width
        val height = gifImage.height * ratio
        val options = RequestOptions().override(screenWidth, height)
        Glide.with(holder.itemView.context)
            .load(gifImage.url)
            .apply(options)
            .centerCrop()
            .into(imageView);
    }

}

class GifViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}

