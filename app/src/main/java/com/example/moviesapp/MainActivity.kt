package com.example.moviesapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var listViewMovies: ListView
    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = BuildConfig.MOVIEDB_API_KEY
        movieRepository = MovieRepository(apiKey)

        listViewMovies = findViewById(R.id.listViewMovies)
        movieAdapter = MovieAdapter(this, mutableListOf())
        listViewMovies.adapter = movieAdapter

        listViewMovies.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedMovie = movieAdapter.getItem(position)
            if (selectedMovie != null) {
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java).apply {
                    putExtra("movieId", selectedMovie.id)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Selected movie is null", Toast.LENGTH_SHORT).show()
            }
        }

        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)

        loadTrendingMovies()

        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                loadTrendingMovies()
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTrendingMovies() {
        movieRepository.getTrendingMoviesMovies(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        movieAdapter.clear()
                        movieAdapter.addAll(movies)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchMovies(query: String) {
        movieRepository.searchMovies(query, object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        movieAdapter.clear()
                        movieAdapter.addAll(movies)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to search movies", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
