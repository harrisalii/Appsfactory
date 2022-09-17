package com.appsfactory.test.domain.album

import android.os.Parcelable
import com.appsfactory.test.domain.artist.Artist
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val name: String,
    val url: String,
    val artist: Artist,
    val imageUrl: String
) : Parcelable
