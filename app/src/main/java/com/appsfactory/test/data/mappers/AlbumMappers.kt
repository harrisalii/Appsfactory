package com.appsfactory.test.data.mappers

import com.appsfactory.test.data.remote.dto.AlbumResultDto
import com.appsfactory.test.domain.album.Album

fun AlbumResultDto.toAlbums(): List<Album> {
    return topAlbums.albums.map {
        it.toAlbum()
    }
}

fun AlbumResultDto.AlbumDto.toAlbum(): Album {
    return Album(
        name = name,
        url = url,
        artist = artistDto.toArtist(),
        imageUrl = images.last().url
    )
}

fun Album.toAlbumDto(): AlbumResultDto.AlbumDto {
    return AlbumResultDto.AlbumDto(
        name = name,
        url = url,
        artistDto = artist.toArtistDto(),
        images = listOf(AlbumResultDto.AlbumImageDto(url = imageUrl))
    )
}
