package com.appsfactory.test.ui.album_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsfactory.test.domain.model.album.Album
import com.appsfactory.test.domain.model.track.Track
import com.appsfactory.test.domain.repository.local.LocalRepository
import com.appsfactory.test.domain.repository.remote.LastFMRepository
import com.appsfactory.test.domain.util.Result
import com.appsfactory.test.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val repository: LastFMRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _album = stateHandle.get<Album>("album")!!
    val album = MutableStateFlow(_album).asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<Track>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _uiEvents = Channel<AlbumDetailEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private var tracks: List<Track>? = null

    init {
        viewModelScope.launch {
            _isFavorite.value = localRepository.isExists(name = _album.name)

            /**
             * Checking if tracks are already present, if album is marked as favorite
             */
            /*if (isFavorite.value) {
                _uiState.apply {
                    emit(UiState.Loading)
                    val tracks = _album.tracks ?: run {
                        localRepository.getAlbum(_album.name)?.tracks
                    }
                    if (tracks.isNullOrEmpty()) {
                        emit(UiState.NoDataFound)
                    } else {
                        emit(UiState.Success(tracks))
                    }
                }
            } else {
                getTracks(_album)
            }*/
        }

        getTracks(_album)
    }

    private fun getTracks(album: Album) = viewModelScope.launch {
        _uiState.emit(UiState.Loading)

        repository.getTracks(
            artist = album.artist.name,
            album = album.name
        ).collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    tracks = result.data
                    if (tracks.isNullOrEmpty()) {
                        _uiState.emit(UiState.NoDataFound)
                    } else {
                        _uiState.emit(UiState.Success(result.data))
                    }
                }
                is Result.Error -> {
                    _uiEvents.send(AlbumDetailEvent.ShowError(result.error))
                }
            }
        }
    }

    fun onTrackClicked(track: Track) = viewModelScope.launch {
        _uiEvents.send(AlbumDetailEvent.OpenTrackUrl(track.url))
    }

    fun onFavoriteClicked() = viewModelScope.launch {
        _isFavorite.value = !isFavorite.value

        if (isFavorite.value) {
            localRepository.insertAlbum(_album.copy(tracks = tracks))
        } else {
            localRepository.deleteAlbum(_album)
        }
    }

    sealed class AlbumDetailEvent {
        data class ShowError(val msg: String) : AlbumDetailEvent()
        data class OpenTrackUrl(val url: String) : AlbumDetailEvent()
    }
}