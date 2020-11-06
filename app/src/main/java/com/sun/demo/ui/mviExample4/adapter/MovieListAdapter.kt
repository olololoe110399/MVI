package com.sun.demo.ui.mviExample4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sun.demo.GlideApp
import com.sun.demo.R
import com.sun.demo.data.model.Movie
import com.sun.demo.util.Constants.BASE_URL_IMAGE
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(private val clickListener: (Movie) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.movieID == newItem.movieID

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            itemView.apply {
                GlideApp.with(this)
                    .load(BASE_URL_IMAGE + movie.movieBackdropPath)
                    .placeholder(R.color.purple_200)
                    .error(android.R.color.holo_red_dark)
                    .dontAnimate()
                    .into(backdropImage)
                titleText.text = movie.movieTitle
                setOnClickListener {
                    clickListener(movie)
                }
            }
        }
    }
}
