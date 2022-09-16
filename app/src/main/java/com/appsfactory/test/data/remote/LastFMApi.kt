package com.appsfactory.test.data.remote

import com.appsfactory.test.BuildConfig
import com.appsfactory.test.data.remote.dto.AlbumResultDto
import com.appsfactory.test.data.remote.dto.ArtistResultDto
import com.appsfactory.test.data.remote.dto.TrackResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMApi {

    private companion object {
        private const val API_KEY = BuildConfig.LAST_FM_API_KEY
    }

    @GET("?method=artist.search&api_key=$API_KEY&format=json")
    suspend fun searchArtist(
        @Query("artist") name: String
    ): Response<ArtistResultDto>

    @GET("?method=artist.gettopalbums&api_key=$API_KEY&format=json")
    suspend fun getTopAlbums(
        @Query("artist") name: String
    ): Response<AlbumResultDto>

    @GET("?method=artist.gettoptracks&api_key=$API_KEY&format=json")
    suspend fun getTopTracks(
        @Query("artist") name: String
    ): Response<TrackResultDto>
}