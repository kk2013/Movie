package com.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.movies.databinding.ActivityMoviesBinding
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMoviesBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies)

        setSupportActionBar(toolbar)
    }
}
