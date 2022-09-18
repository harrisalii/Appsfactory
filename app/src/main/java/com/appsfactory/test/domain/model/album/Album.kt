package com.appsfactory.test.domain.model.album

import android.os.Parcelable
import com.appsfactory.test.domain.model.artist.Artist
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val name: String,
    val url: String,
    val artist: Artist,
    val imageUrl: String
) : Parcelable
