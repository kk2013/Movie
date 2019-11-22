package com.movies.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.R
import com.movies.databinding.FragmentMoviesBinding
import com.movies.di.ViewModelFactory
import com.movies.movielist.data.NetworkState
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

    private val moviesAdapter: MoviesAdapter = MoviesAdapter()
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        val moviesRecyclerView = binding.moviesRecyclerView
        moviesRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
            object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    moviesRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                    val width = moviesRecyclerView.measuredWidth
                    val width = resources.displayMetrics.widthPixels
                    val widthDp = resources.displayMetrics.widthPixels/resources.displayMetrics.density
                    val imageWidth = resources.getDimension(R.dimen.image_width)
                    val spanCount = (width/imageWidth).roundToInt()
                    Log.i("MOVIESLOG", "$width $widthDp $imageWidth $spanCount")
                    gridLayoutManager.spanCount = spanCount
                    gridLayoutManager.requestLayout()
                }
            }
        )

        gridLayoutManager = GridLayoutManager(activity, 2)
        binding.moviesRecyclerView.layoutManager = gridLayoutManager
        binding.moviesRecyclerView.adapter = moviesAdapter

        moviesViewModel.moviesRepo.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
        moviesViewModel.networkState?.observe(this, Observer {
            when(it) {
                NetworkState.LOADING -> progress_bar.visibility = View.VISIBLE
                NetworkState.SUCCESS -> progress_bar.visibility = View.GONE
                NetworkState.FAILED -> progress_bar.visibility = View.GONE
            }
//            moviesAdapter.setNetworkState(it)
        })

        return binding.root
    }
}
