package com.movies.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.databinding.FragmentMoviesBinding
import com.movies.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MoviesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val moviesViewModel: MoviesViewModel by viewModels {
        viewModelFactory
    }

    private val moviesAdapter: MoviesAdapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.moviesRecyclerView.adapter = moviesAdapter

        moviesViewModel.moviesRepo.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
        moviesViewModel.networkState?.observe(this, Observer {
            moviesAdapter.setNetworkState(it)
        })

        return binding.root
    }
}
