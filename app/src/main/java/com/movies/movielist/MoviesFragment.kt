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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.R
import com.movies.data.NetworkState
import com.movies.databinding.FragmentMoviesBinding
import com.movies.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.math.roundToInt

class MoviesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val moviesViewModel: MoviesViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var layoutManager: LinearLayoutManager
    private val moviesAdapter: MoviesAdapter = MoviesAdapter()
//    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

//        val moviesRecyclerView = binding.moviesRecyclerView
        /*moviesRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
            object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    moviesRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val width = moviesRecyclerView.measuredWidth
                    val imageWidth = resources.getDimension(R.dimen.image_width)
                    val spanCount = (width/imageWidth).roundToInt()
                    gridLayoutManager.spanCount = spanCount
                    gridLayoutManager.requestLayout()
                }
            }
        )

        gridLayoutManager = GridLayoutManager(activity, 2)
*/
//        gridLayoutManager = LinearLayoutManager(activity)
        layoutManager = LinearLayoutManager(activity)
        binding.moviesRecyclerView.layoutManager = layoutManager//gridLayoutManager
        binding.moviesRecyclerView.adapter = moviesAdapter

        moviesViewModel.moviesRepo.observe(this, Observer {
            Log.i("MOVIELOG", "Movies: ${it.size}")
            moviesAdapter.submitList(it)
        })
        moviesViewModel.networkState?.observe(this, Observer {
            Log.i("MOVIELOG1", "${it.status.name}")
            moviesAdapter.setNetworkState(it)
        })

        return binding.root
    }
}
