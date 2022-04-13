package com.vinappstudio.movieimdb.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.vinappstudio.movieimdb.R

class DetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // find views where we preview the incoming data
        val imageView = findViewById<ImageView>(R.id.dimageView)
        val rating_tv = findViewById<TextView>(R.id.imDbRating_id)
        val title_tv = findViewById<TextView>(R.id.dmovie_id)

        val rank_tv = findViewById<TextView>(R.id.rank_Id)
        val fullTitle_tv = findViewById<TextView>(R.id.fullTitle_Id)
        val year_tv = findViewById<TextView>(R.id.year_ID)
        val crew_tv = findViewById<TextView>(R.id.crew_Id)
        val ratingCount_tv = findViewById<TextView>(R.id.imDbRatingCount_id)



        // get data sent from main activity  through bundle
        val bundle = intent.extras
        val mTitle = bundle!!.getString("title")
        val mPoster = bundle.getString("poster")
        val mRating = bundle.getDouble("rating")

        val mRank  = bundle.getInt("rank")
        val mFullTitle = bundle.getString("fullTitle")
        val mYear = bundle.getInt("year")
        val mCrew = bundle.getString("crew")
        val mImDbRatingCount = bundle.getInt("imDbRatingCount")

        // use glide library to show images
        Glide.with(this).load(mPoster).into(imageView)

        //  show data in detail activity
        rating_tv.text = "Rating: "+mRating.toString()
        title_tv.text = "Title: "+mTitle
        rank_tv.text = "Rank: "+mRank.toString()
        fullTitle_tv.text = "Full Title: "+mFullTitle
        year_tv.text = "Year: "+mYear.toString()
        crew_tv.text= "Crew: "+mCrew
        ratingCount_tv.text = "IMDB Rating Count: "+mImDbRatingCount.toString()

    }
}