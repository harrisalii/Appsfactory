package com.appsfactory.test.ui.search_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsfactory.test.domain.artist.Artist
import com.appsfactory.test.domain.repository.LastFMRepository
import com.appsfactory.test.domain.util.Result
import com.appsfactory.test.ui.home.HomeViewModel
import com.appsfactory.test.utils.logDebug
import com.appsfactory.test.utils.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(
    private val repository: LastFMRepository
) : ViewModel() {

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists.asStateFlow()

    init {
        viewModelScope.launch {
            repository.searchArtist(name = "cher").collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        logDebug<HomeViewModel>(result.data.toString())
                        logDebug<HomeViewModel>("Success!")
                        _artists.value = result.data
                    }
                    is Result.Error -> {
                        logError<SearchArtistViewModel>(result.error)
                    }
                }
            }
        }
    }
}