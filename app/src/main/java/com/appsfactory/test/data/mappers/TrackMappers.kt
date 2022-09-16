package com.appsfactory.test.data.mappers

import com.appsfactory.test.data.remote.dto.TrackResultDto
import com.appsfactory.test.domain.track.Track

fun TrackResultDto.toTracks(): List<Track> {
    return topTracks.tracks.map {
        it.toTrack()
    }
}

fun TrackResultDto.TrackDto.toTrack(): Track {
    return Track(
        name = name,
        url = url,
        artist = artistDto.toArtist(),
        imageUrl = images.last().url
    )
}

fun Track.toTrackDto(): TrackResultDto.TrackDto {
    return TrackResultDto.TrackDto(
        name = name,
        url = url,
        artistDto = artist.toArtistDto(),
        images = listOf(TrackResultDto.TrackImageDto(url = imageUrl))
    )
}
