package com.appsfactory.test.data.repository

import com.appsfactory.test.data.mappers.toAlbums
import com.appsfactory.test.data.mappers.toArtists
import com.appsfactory.test.data.mappers.toTracks
import com.appsfactory.test.data.remote.LastFMApi
import com.appsfactory.test.domain.album.Album
import com.appsfactory.test.domain.artist.Artist
import com.appsfactory.test.domain.repository.LastFMRepository
import com.appsfactory.test.domain.track.Track
import com.appsfactory.test.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LastFMRepositoryImpl @Inject constructor(
    private val api: LastFMApi
) : LastFMRepository, BaseRepository() {

    override suspend fun searchArtist(name: String): Flow<Result<List<Artist>>> {
        return makeRequest(
            request = {
                api.searchArtist(name = name)
            },
            response = {
                toArtists()
            }
        )
    }

    override suspend fun getTopAlbumsOfArtist(name: String): Flow<Result<List<Album>>> {
        return makeRequest(
            request = {
                api.getTopAlbums(name = name)
            },
            response = {
                toAlbums()
            }
        )
    }

    override suspend fun getTracks(artist: String, album: String): Flow<Result<List<Track>>> {
        return makeRequest(
            request = {
                api.getTracks(artist = artist, album = album)
            },
            response = {
                toTracks()
            }
        )
    }
}