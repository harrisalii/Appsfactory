package com.appsfactory.test.ui.search_artist

import androidx.lifecycle.viewModelScope
import com.appsfactory.test.domain.artist.Artist
import com.appsfactory.test.domain.repository.LastFMRepository
import com.appsfactory.test.domain.util.Result
import com.appsfactory.test.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(
    private val repository: LastFMRepository
) : BaseViewModel() {

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists.asStateFlow()

    private val _uiEvents = Channel<SearchArtistEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun searchArtist(query: String) = viewModelScope.launch {
        showLoader()
        repository.searchArtist(query).collectLatest { result ->
            hideLoader()
            when (result) {
                is Result.Success -> {
                    _artists.value = result.data
                }
                is Result.Error -> {
                    _uiEvents.send(SearchArtistEvent.ShowError(result.error))
                }
            }
        }
    }

    fun onSearchClicked(query: String) {
        if (query.isBlank()) return
        searchArtist(query)
    }

    fun onArtistClicked(artist: Artist) = viewModelScope.launch {
        _uiEvents.send(SearchArtistEvent.NavigateToAlbumScreen(artist))
    }

    sealed class SearchArtistEvent {
        data class NavigateToAlbumScreen(val artist: Artist) : SearchArtistEvent()
        data class ShowError(val error: String) : SearchArtistEvent()
    }
}