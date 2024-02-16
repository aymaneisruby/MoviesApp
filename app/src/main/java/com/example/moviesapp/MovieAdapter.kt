package com.example.moviesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.squareup.picasso.Picasso

class MovieAdapter(
    context: Context,
    private val movies: List<Movie>
) : ArrayAdapter<Movie>(context, 0, movies) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
            holder = ViewHolder()
            holder.imageViewPoster = view.findViewById(R.id.imageViewPoster)
            holder.textViewTitle = view.findViewById(R.id.textViewTitle)
            holder.textViewYear= view.findViewById(R.id.textViewYear)
            holder.textVoteAverage = view.findViewById(R.id.textVoteAverage)
            holder.textViewOverview = view.findViewById(R.id.textViewOverview)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val movie = movies[position]
        holder.textViewTitle.text = movie.title
        holder.textViewYear.text = extractYear(movie.release_date)
        holder.textVoteAverage.text =  "%.1f".format(movie.vote_average.toFloatOrNull() ?: 0.0f)+"/10"
        holder.textViewOverview.text = movie.overview.take(118).plus("...")


        val imagePath = movie.poster_path
        val baseUrl = "https://image.tmdb.org/t/p/w500/"
        val imageUrl = baseUrl + imagePath
        println(imageUrl)

        val resources: Resources = context.resources

        val errorDrawable = ResourcesCompat.getDrawable(resources, R.drawable.no_poster, null)
        val scaledErrorDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((errorDrawable as BitmapDrawable).bitmap, 180, 250, true))
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(scaledErrorDrawable)
                .into(holder.imageViewPoster)

        return view!!
    }

    private class ViewHolder {
        lateinit var imageViewPoster: ImageView
        lateinit var textViewTitle: TextView
        lateinit var textViewYear: TextView
        lateinit var textVoteAverage: TextView
        lateinit var textViewOverview: TextView

    }

    private fun extractYear(releaseDate: String?): String {
        if (!releaseDate.isNullOrEmpty() && releaseDate.length >= 4) {
            return releaseDate.substring(0, 4)
        }
        return ""
    }
}