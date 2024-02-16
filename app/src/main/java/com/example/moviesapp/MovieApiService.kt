package com.example.moviesapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("discover/movie")
    fun getTrendingMoviesMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("search/movie")
    fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<Movie>
}