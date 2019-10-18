package com.movies.di

import com.movies.MovieDetailsFragment
import com.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MoviesModule {

    internal abstract fun moviesFragment(): MoviesFragment

    internal abstract fun movieDetailsFragment(): MovieDetailsFragment
}
