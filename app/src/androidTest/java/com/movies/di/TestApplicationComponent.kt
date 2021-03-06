package com.movies.di

import android.content.Context
import com.movies.MoviesApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import okhttp3.mockwebserver.MockWebServer

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, TestNetworkModule::class, MoviesModule::class])
interface TestApplicationComponent : ApplicationComponent {

    fun getMockWebserver(): MockWebServer

    override fun inject(app: MoviesApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
    }
}
