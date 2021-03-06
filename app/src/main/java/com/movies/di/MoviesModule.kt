package com.movies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.moviedetails.MovieDetailsFragment
import com.movies.moviedetails.MovieDetailsViewModel
import com.movies.movielist.MoviesFragment
import com.movies.movielist.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MoviesModule {

    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    @ContributesAndroidInjector
    internal abstract fun moviesFragment(): MoviesFragment

    @ContributesAndroidInjector
    internal abstract fun movieDetailsFragment(): MovieDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindsMoviesViewModel(viewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindsMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}
