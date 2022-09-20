package com.appsfactory.test.ui.home

import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.filters.MediumTest
import com.appsfactory.test.R
import com.appsfactory.test.domain.model.album.Album
import com.appsfactory.test.domain.model.artist.Artist
import com.appsfactory.test.launchFragmentInHiltContainer
import com.appsfactory.test.ui.album.AlbumAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickOnAlbum_navigateToAlbumDetailsFragment() {
        val navController = mock(NavController::class.java)

        val testAlbum = Album(
            "",
            "",
            Artist("", "", ""),
            "",
            listOf()
        )

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
            /*
            * Need to make albumAdapter public for test to succeed.
            * */
            //albumAdapter.submitList(listOf(testAlbum))
        }

        onView(withId(R.id.album_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(
                0,
                click()
            )
        )

        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToAlbumDetailFragment(testAlbum)
        )
    }

    @Test
    fun clickOnSearchButton_navigateToSearchArtistFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(
            allOf(
                instanceOf(AppCompatImageButton::class.java), withParent(withId(R.id.toolbar))
            )
        ).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionMainActivityToSearchArtistFragment()
        )
    }
}