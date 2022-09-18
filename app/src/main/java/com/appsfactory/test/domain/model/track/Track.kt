package com.appsfactory.test.domain.model.track

import com.appsfactory.test.domain.model.artist.Artist

data class Track(
    val name: String,
    val url: String,
    val artist: Artist,
    val duration: String
)
