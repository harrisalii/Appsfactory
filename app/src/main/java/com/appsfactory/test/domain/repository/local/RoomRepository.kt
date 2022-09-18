package com.appsfactory.test.domain.repository.local

import com.appsfactory.test.domain.album.Album
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun insertAlbum(album: Album)

    suspend fun insertAlbums(albums: List<Album>)

    suspend fun deleteAlbum(album: Album)

    suspend fun deleteAllAlbums()

    suspend fun isExists(name: String): Boolean

    fun getAllAlbums(): Flow<List<Album>>
}