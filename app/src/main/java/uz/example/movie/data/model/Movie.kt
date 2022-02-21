package uz.example.movie.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id:Int,
    @SerializedName("original_title")
    val originalTitle:String,
    val overview:String,
    @SerializedName("poster_path")
    val posterPath:String,
    val title:String,
    @SerializedName("vote_average")
    val voteAverage:Double
):Parcelable