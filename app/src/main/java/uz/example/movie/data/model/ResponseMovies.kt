package uz.example.movie.data.model

import com.google.gson.annotations.SerializedName


data class ResponseMovies(
    var page:String="",
    var results:List<Movie> = listOf(),
    @SerializedName("total_results")
    var totalResults:Int=0,
    @SerializedName("total_pages")
    var totalPages:Int=0
)