package com.vinappstudio.movieimdb.Data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinappstudio.movieimdb.Activity.DetailActivity
import com.vinappstudio.movieimdb.Model.Movie
import com.vinappstudio.movieimdb.R

class MovieAdapter(private val context: Context, private val movieList: List<Movie>?) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MovieHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        // variable movie will store position of  objects  inside movieList
        // so that we able to know which item is clicked
        val movie = movieList!![position]

        holder.rating.text = movie.rating.toString()
        holder.title.text = movie.title

        // used glide library to get poster
        Glide.with(context).load(movie.poster).into(holder.imageView)
        holder.constraintLayout.setOnClickListener {

            // switch to another activity  while click that item , with its data
            val intent = Intent(context, DetailActivity::class.java)
            val bundle = Bundle()
            // sent data through bundle
            bundle.putString("title", movie.title)
            bundle.putString("poster", movie.poster)
            bundle.putDouble("rating", movie.rating)

            bundle.putInt("rank", movie.rank)
            bundle.putString("fullTitle",movie.fullTitle)
            bundle.putInt("year",movie.year)
            bundle.putString("crew",movie.crew)
            bundle.putInt("imDbRatingCount",movie.imDbRatingCount)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        // know no of item in the list
        return movieList!!.size
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var title: TextView
        var rating: TextView
        var constraintLayout: ConstraintLayout

        init {
            imageView = itemView.findViewById(R.id.image_item_id)
            title = itemView.findViewById(R.id.movie_item_id)
            rating = itemView.findViewById(R.id.rating_item_id)
            constraintLayout = itemView.findViewById(R.id.item_layout)
        }
    }
}