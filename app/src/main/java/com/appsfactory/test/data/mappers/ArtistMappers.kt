package com.appsfactory.test.data.mappers

import com.appsfactory.test.data.remote.dto.ArtistResultDto
import com.appsfactory.test.domain.artist.Artist

fun ArtistResultDto.toArtists(): List<Artist> {
    return results.artistMatches.artists.map { artist ->
        artist.toArtist()
    }
}

fun ArtistResultDto.ArtistDto.toArtist(): Artist {
    return Artist(
        name = name,
        url = url,
        imageUrl = images?.last()?.url.orEmpty()
    )
}

fun Artist.toArtistDto(): ArtistResultDto.ArtistDto {
    return ArtistResultDto.ArtistDto(
        name = name,
        url = url,
        images = listOf(ArtistResultDto.ArtistImageDto(url = imageUrl))
    )
}