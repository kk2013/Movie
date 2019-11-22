package com.movies.tests

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.chuck.util.EspressoIdlingResource
import com.movies.MoviesActivity
import com.movies.MoviesApplication
import com.movies.R
import com.movies.di.DaggerTestApplicationComponent
import com.movies.movielist.MovieViewHolder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesTest {

    @get:Rule
    var activityRule: ActivityTestRule<MoviesActivity> =
        ActivityTestRule(MoviesActivity::class.java)

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as MoviesApplication
        mockWebServer = DaggerTestApplicationComponent.factory().create(app).getMockWebserver()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

        val intent = Intent(
            InstrumentationRegistry.getInstrumentation()
                .targetContext, MoviesActivity::class.java
        )

        activityRule.launchActivity(intent)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun getMoviesSucces() {
        val moviesResponse =
            InstrumentationRegistry.getInstrumentation().context.assets.open("movies.json")
                .bufferedReader().use { it.readText() }
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(moviesResponse))

        val detailsResponse =
            InstrumentationRegistry.getInstrumentation().context.assets.open("movie_details.json")
                .bufferedReader().use { it.readText() }
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(detailsResponse))

        onView(withId(R.id.movies_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(
                ITEM_BELOW_THE_FOLD,
                click()
            )
        )

        onView(withId(R.id.title)).check(matches(withText((TITLE))))
    }

    companion object {
        const val ITEM_BELOW_THE_FOLD = 10
        const val TITLE = "Mission: Impossible - Fallout"
    }
}
