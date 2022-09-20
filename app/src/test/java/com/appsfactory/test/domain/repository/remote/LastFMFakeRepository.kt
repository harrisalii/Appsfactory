package com.appsfactory.test.domain.repository.remote

import com.appsfactory.test.domain.model.album.Album
import com.appsfactory.test.domain.model.artist.Artist
import com.appsfactory.test.domain.model.track.Track
import com.appsfactory.test.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class LastFMFakeRepository : LastFMRepository {

    private val albums = mutableListOf<Album>()

    private fun refreshFlow() {

    }

    private var shouldReturnNetworkError = false

    private val flowOfArtists = MutableStateFlow<List<Artist>>(emptyList())
    private val flowOfTracks = MutableStateFlow<List<Track>>(emptyList())
    private val flowOfAlbums = MutableStateFlow<List<Album>>(emptyList())

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun searchArtist(name: String): Flow<Result<List<Artist>>> {
        return flow {
            if (shouldReturnNetworkError) {
                emit(Result.Error("Error occurred"))
            } else {
                emit(Result.Success(listOf()))
            }
        }
    }

    override suspend fun getTopAlbumsOfArtist(name: String): Flow<Result<List<Album>>> {
        return flow {
            if (shouldReturnNetworkError) {
                emit(Result.Error("Error occurred"))
            } else {
                emit(Result.Success(listOf()))
            }
        }
    }

    override suspend fun getTracks(artist: String, album: String): Flow<Result<List<Track>>> {
        return flow {
            if (shouldReturnNetworkError) {
                emit(Result.Error("Error occurred"))
            } else {
                emit(Result.Success(listOf()))
            }
        }
    }
}