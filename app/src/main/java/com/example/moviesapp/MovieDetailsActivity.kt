package com.example.moviesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val apiKey = BuildConfig.MOVIEDB_API_KEY
        movieRepository = MovieRepository(apiKey)

        val movieId = intent.getIntExtra("movieId", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        } else {
            Toast.makeText(this, "Movie ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        movieRepository.getMovieDetails(movieId, object : Callback<Movie> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val detailedMovie = response.body()
                    if (detailedMovie != null) {
                        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
                        val textViewReleaseDate = findViewById<TextView>(R.id.textViewReleaseDate)
                        val textViewOverview = findViewById<TextView>(R.id.textViewOverview)
                        val imageViewPoster = findViewById<ImageView>(R.id.imageViewPoster)
                        textViewTitle.text = detailedMovie.title
                        textViewReleaseDate.text = extractYear(detailedMovie.release_date)+ " - " +  "%.1f".format(detailedMovie.vote_average.toFloatOrNull() ?: 0.0f)+"/10"
                        textViewOverview.text = detailedMovie.overview
                        val baseUrl = "https://image.tmdb.org/t/p/w500"
                        val moviePosterPath = baseUrl+detailedMovie.poster_path
                        if (!moviePosterPath.isNullOrEmpty()) {
                            Picasso.get().load(moviePosterPath)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.no_poster)
                                .into(imageViewPoster)
                        } else {
                            imageViewPoster.setImageResource(R.drawable.placeholder)
                        }
                    } else {
                        Toast.makeText(this@MovieDetailsActivity, "Failed to fetch movie details", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MovieDetailsActivity, "Failed to fetch movie details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(this@MovieDetailsActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun extractYear(releaseDate: String?): String {
        if (!releaseDate.isNullOrEmpty() && releaseDate.length >= 4) {
            return releaseDate.substring(0, 4)
        }
        return ""
    }
}