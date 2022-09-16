package com.appsfactory.test.domain.album

import com.appsfactory.test.domain.artist.Artist

data class Album(
    val name: String,
    val url: String,
    val artist: Artist,
    val imageUrl: String
)
