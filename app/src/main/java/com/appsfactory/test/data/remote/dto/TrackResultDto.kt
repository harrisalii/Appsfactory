package com.appsfactory.test.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrackResultDto(
    @SerializedName("toptracks")
    val topTracks: TopTrackDto
) {

    data class TopTrackDto(
        @SerializedName("track")
        val tracks: List<TrackDto>
    )

    data class TrackDto(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("artist")
        val artistDto: ArtistResultDto.ArtistDto,
        @SerializedName("image")
        val images: List<TrackImageDto>
    )

    data class TrackImageDto(
        @SerializedName("#text")
        val url: String
    )
}