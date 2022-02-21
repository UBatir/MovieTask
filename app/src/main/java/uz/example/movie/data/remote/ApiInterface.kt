package uz.example.movie.data.remote

import retrofit2.Response
import retrofit2.http.*
import uz.example.movie.data.model.*

interface ApiInterface {

    @GET("search/movie/")
    suspend fun getMovies(
        @Query("api_key") apiKey:String,
        @Query("query") query:String
    ):Response<ResponseMovies>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey:String
    ):Response<Credit>

    @POST("account/{account_id}/favorite")
    suspend fun addMovieToFavorite(
        @Query("api_key") apiKey:String,
        @Query("session_id") sessionId:String,
        @Body setFavorite: Favorite
    ):Response<Unit>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Query("api_key") apiKey:String,
        @Query("session_id") sessionId:String
    ):Response<ResponseMovies>
}