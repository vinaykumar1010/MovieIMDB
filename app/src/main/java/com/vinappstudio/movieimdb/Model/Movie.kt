package com.vinappstudio.movieimdb.Model

import java.io.Serializable

class Movie(val title: String, val poster: String, val rating: Double, val rank:Int,val fullTitle:String,val year:Int,val crew:String,val imDbRatingCount:Int) :
    Serializable