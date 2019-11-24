package com.movies

import com.movies.di.DaggerTestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MoviesTestApplication : MoviesApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTestApplicationComponent.factory().create(applicationContext)
    }
}
