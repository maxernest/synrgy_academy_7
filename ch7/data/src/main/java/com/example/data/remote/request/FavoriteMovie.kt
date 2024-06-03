package com.example.data.remote.request

import com.google.gson.annotations.SerializedName

data class FavoriteMovie(
    @SerializedName("media_id")
    var mediaId: Int,

    @SerializedName("media_type")
    var mediaType:String = "movie",

    var favorite:Boolean = true
)
