package com.appsfactory.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsfactory.test.data.remote.dto.AlbumResultDto
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumResultDto.AlbumDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumResultDto.AlbumDto>)

    @Query("SELECT * FROM AlbumDto ORDER BY id DESC")
    fun getAllWallpapers(): Flow<List<AlbumResultDto.AlbumDto>>

    @Query("DELETE FROM AlbumDto")
    suspend fun deleteAllAlbums()
}