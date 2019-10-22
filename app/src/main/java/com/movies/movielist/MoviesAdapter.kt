package com.movies.movielist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.R
import com.movies.data.NetworkState
import com.movies.model.Movie

class MoviesAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var networkState: NetworkState? = NetworkState.LOADING

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("MOVIELOG", "onBindViewHolder")
        when (getItemViewType(position)) {
            R.layout.movie_item_row -> (holder as MovieViewHolder).bind(getItem(position))
            R.layout.progress_item_row -> (holder as ProgressViewHolder)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.i("MOVIELOG", "In payload")
        if (payloads.isNotEmpty()) {
            val movie = getItem(position)
            Log.i("MOVIELOG", "Poster path: ${movie?.poster_path}")
            (holder as MovieViewHolder).bind(movie)
        } else {
            Log.i("MOVIELOG", "Payload empty")
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("MOVIELOG", "onCreateHolder")
        return when (viewType) {
            R.layout.movie_item_row -> MovieViewHolder.create(parent)
            R.layout.progress_item_row -> ProgressViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    override fun getItemViewType(position: Int): Int {
        Log.i("MOVIELOG", "getItemViewType")
        return if (hasExtraRow() && position == itemCount - 1) {
            Log.i("MOVIELOG", "Progress item")
            R.layout.progress_item_row
        } else {
            Log.i("MOVIELOG", "Movie item")
            R.layout.movie_item_row
        }
    }

    private fun hasExtraRow(): Boolean =
        networkState != null && networkState != NetworkState.SUCCESS

    fun setNetworkState(newNetworkState: NetworkState?) {
        Log.i("MOVIELOG", "Set network state $networkState $newNetworkState")
        val previousState = networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                Log.i("MOVIELOG", "Had extra row "+super.getItemCount())
                notifyItemRemoved(super.getItemCount())
            } else {
                Log.i("MOVIELOG", "Item inserted "+super.getItemCount())
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            Log.i("MOVIELOG", "Item changed")
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {

        private const val LOG_TAG = "Adapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {

                /*Log.i("MOVIELOG", "areItemsSame")
                val isSame = oldItem.id == newItem.id
                Log.i("MOVIELOG", "isSame: $isSame")
                return oldItem.id == newItem.id*/
                return false
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            /*    Log.i("MOVIELOG", "areContentsSame")
                val areContentsTheSame = oldItem == newItem
                Log.i("MOVIELOG", "areContentsTheSame: $areContentsTheSame")
                return oldItem == newItem*/
            return false
            }
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun create(parent: ViewGroup): ProgressViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_item_row, parent, false)
                return ProgressViewHolder(view)
            }
        }
    }
}
