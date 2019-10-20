package com.movies.movielist

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
        when(getItemViewType(position)) {
            R.layout.movie_item_row -> (holder as MovieViewHolder).bind(getItem(position))
            R.layout.progress_item_row -> (holder as ProgressViewHolder)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val movie = getItem(position)
            (holder as MovieViewHolder).bind(movie)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.movie_item_row -> MovieViewHolder.create(parent)
            R.layout.progress_item_row -> ProgressViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.progress_item_row
        } else {
            R.layout.movie_item_row
        }
    }

    private fun hasExtraRow(): Boolean = networkState != null && networkState != NetworkState.SUCCESS

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {

        private const val LOG_TAG = "Adapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }

    class ProgressViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun create(parent: ViewGroup): ProgressViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_item_row, parent, false)
                return ProgressViewHolder(view)
            }
        }
    }
}
