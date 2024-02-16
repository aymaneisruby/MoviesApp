package com.example.moviesapp

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository(private val apiKey: String) {
    private val apiService: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    fun getTrendingMoviesMovies(callback: Callback<MovieResponse>) {
        apiService.getTrendingMoviesMovies(apiKey).enqueue(callback)
    }

    fun searchMovies(query: String, callback: Callback<MovieResponse>) {
        apiService.searchMovies(apiKey, query).enqueue(callback)
    }

    fun getMovieDetails(movieId: Int, callback: Callback<Movie>) {
        apiService.getMovieDetails(movieId, apiKey).enqueue(callback)
    }
}
