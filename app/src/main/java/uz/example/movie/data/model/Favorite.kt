package uz.example.movie.data.model

import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("media_type")
    val mediaType:String="movie",
    @SerializedName("media_id")
    val mediaId:Int,
    val favorite: Boolean=true
)
