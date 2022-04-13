package com.vinappstudio.movieimdb.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.vinappstudio.movieimdb.Model.Movie
import com.vinappstudio.movieimdb.Data.MovieAdapter
import com.vinappstudio.movieimdb.R
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    // lateinit is used  for declaration  rather then declaring these objects null
    // because !! operator is used while setting up adapter inside RecyclerView
    // this operator means assert  value if non null, and if we declare null then
    // recycler View  is always empty.


    private lateinit var adapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var requestQueue: RequestQueue
    private var movieList: MutableList<Movie>? = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView= findViewById<RecyclerView>(R.id.recyclerview_id)
        recyclerView.setHasFixedSize(true)

        //to show items like grid view in recycler View Simply replace LinearLayoutManager with GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(this,3)

        // Rest Api Volley is used here
        requestQueue = Volley.newRequestQueue(this);
        /**
         * If your application makes constant use of the network, it's probably most efficient to set up a single
         * instance of RequestQueue that will last the lifetime of your app. You can achieve this in various ways.
         * The recommended approach is to implement a singleton class that encapsulates RequestQueue and other Volley
         * functionality. Another approach is to subclass Application and set up the RequestQueue in
         * Application.onCreate(). But this approach is discouraged; a static singleton can provide the same
         * functionality in a more modular way.
         */
        movieList = ArrayList()
        fetchMovies()
    }


    companion object {
        // Lets Parse IMDB Api ;)
        private const val url = "https://imdb-api.com/en/API/Top250Movies/k_a13wlqqb"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchMovies() {
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("TAG", "response$response")
                try {
                    val jasonArray = response.getJSONArray("items")
                    for (i in 0 until jasonArray.length()) {
                        val jsonObject = jasonArray.getJSONObject(i)
                        val rank = jsonObject.getInt("rank")
                        val title = jsonObject.getString("title")
                        val fullTitle = jsonObject.getString("fullTitle")
                        val year = jsonObject.getInt("year")
                        val poster = jsonObject.getString("image")
                        val crew = jsonObject.getString("crew")
                        val rating = jsonObject.getDouble("imDbRating")
                        val imDbRatingCount = jsonObject.getInt("imDbRatingCount")

                        val movie = Movie(title, poster, rating,rank,fullTitle,year,crew,imDbRatingCount)
                        movieList!!.add(movie)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                adapter = MovieAdapter(this@MainActivity, movieList)
                recyclerView!!.adapter= adapter
                adapter.notifyDataSetChanged()
            }
        ) // as we dealing with internet something might go wrong
        { error -> Toast.makeText(this@MainActivity, "check internet connection & RESTART APP : OR"+ error.message, Toast.LENGTH_LONG).show()
        }
        // as if everything  is put into queue,  that way there is no clashes
        requestQueue.add(objectRequest)
    }
    // Search is partially implemented right now
    //this method is used to search inside recycler View
    /** we can also use JSON Searching  : How ?
     * 1 first store the search text inside Shared Preference
     * 2 pass that String(Search Text) inside fetch movies  Method
     * 3 Concatenate that text with the url
     * 4 Now we get all search result EVEN if some of them not loaded into RecyclerView
     * 5 this is powerful and Efficient way
     * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Type here to search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                // this method called when user search the old typed Text
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                // this method is called when one or every character inside input changes
                recyclerView.filterTouchesWhenObscured
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}