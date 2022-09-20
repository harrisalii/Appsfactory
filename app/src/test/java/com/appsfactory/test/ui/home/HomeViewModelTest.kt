package com.appsfactory.test.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appsfactory.test.MainDispatcherRule
import com.appsfactory.test.domain.model.album.Album
import com.appsfactory.test.domain.model.artist.Artist
import com.appsfactory.test.domain.repository.local.LocalFakeRepository
import com.appsfactory.test.domain.util.UiState
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @Test
    fun whenAlbumsAreEmpty_returnsNoDataFoundState() = runTest {
        val repository = LocalFakeRepository()
        viewModel = HomeViewModel(repository)

        val state = viewModel.uiState.value is UiState.NoDataFound
        Truth.assertThat(state).isTrue()
    }

    @Test
    fun whenAlbumsAreNotEmpty_returnsSuccessState() = runBlocking {
        val repository = LocalFakeRepository()
        repository.insertAlbum(Album("name, ", "", Artist("", "", ""), "", listOf()))

        viewModel = HomeViewModel(repository)

        val state = viewModel.uiState.value is UiState.Success
        Truth.assertThat(state).isTrue()
    }

    /*@Test
    fun whenInitializedSate_returnsLoadingState() = runTest {
        val repository = LocalFakeRepository()

        viewModel = HomeViewModel(repository)

        val state = viewModel.uiState.value is UiState.Loading
        Truth.assertThat(state).isTrue()
    }*/
}