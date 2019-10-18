package com.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movies.databinding.FragmentMoviesBinding
import dagger.android.support.DaggerFragment

class MoviesFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }
}
