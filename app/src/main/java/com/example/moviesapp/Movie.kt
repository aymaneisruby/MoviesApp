package com.example.moviesapp

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path : String,
    val release_date : String,
    val vote_average : String
)