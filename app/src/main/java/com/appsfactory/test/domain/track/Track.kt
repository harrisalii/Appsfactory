package com.appsfactory.test.domain.track

import com.appsfactory.test.domain.artist.Artist

data class Track(
    val name: String,
    val url: String,
    val artist: Artist,
    val imageUrl: String
)
