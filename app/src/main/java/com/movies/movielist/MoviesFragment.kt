package com.movies.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.R
import com.movies.data.NetworkState
import com.movies.databinding.FragmentMoviesBinding
import com.movies.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject
import kotlin.math.roundToInt

class MoviesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val moviesViewModel: MoviesViewModel by viewModels {
        viewModelFactory
    }

    private val moviesAdapter: MoviesAdapter =
        MoviesAdapter(onClickListener = { movieId -> getMovieDetails(movieId) })

    private fun getMovieDetails(movieId: String) {
        val action = MoviesFragmentDirections.getMoviesDetails(movieId)
        findNavController().navigate(action)
    }

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        val moviesRecyclerView = binding.moviesRecyclerView
        moviesRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    moviesRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val width = resources.displayMetrics.widthPixels
                    val imageWidth = resources.getDimension(R.dimen.image_width)
                    val spanCount = (width / imageWidth).roundToInt()
                    gridLayoutManager.spanCount = spanCount
                    gridLayoutManager.requestLayout()
                }
            }
        )

        gridLayoutManager = GridLayoutManager(activity, 2)
        binding.moviesRecyclerView.layoutManager = gridLayoutManager
        binding.moviesRecyclerView.adapter = moviesAdapter

        moviesViewModel.moviesList.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
        moviesViewModel.networkState?.observe(this, Observer {
            when (it) {
                NetworkState.Loading -> progress_bar.visibility = View.VISIBLE
                NetworkState.Success -> progress_bar.visibility = View.GONE
                NetworkState.Failed -> progress_bar.visibility = View.GONE
            }
        })

        return binding.root
    }
}
