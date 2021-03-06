package uz.example.movie.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.example.movie.data.model.Credit
import uz.example.movie.data.model.Favorite
import uz.example.movie.data.model.ResponseMovies
import uz.example.movie.data.remote.ApiInterface

class MainRepository(private val api: ApiInterface) {

    val API_KEY="4e2c762899812f405266d2b87ab36856"
    val SESSION_ID="d2e82d1c0dc65178821cd029af47f168a38f9e72"

    fun getMovies(query:String): Flow<Result<ResponseMovies>> = flow{
        val response=api.getMovies(API_KEY,query)
        if (response.isSuccessful){
            emit(Result.success(response.body()!!))
        }
        else{
            emit(Result.failure<ResponseMovies>(Throwable("Произошла ошибка")))
        }
    }.flowOn(Dispatchers.IO)

    fun getFavoriteMovies(): Flow<Result<ResponseMovies>> = flow{
        val response=api.getFavoriteMovies(API_KEY,SESSION_ID)
        if (response.isSuccessful){
            emit(Result.success(response.body()!!))
        }
        else{
            emit(Result.failure<ResponseMovies>(Throwable("Произошла ошибка")))
        }
    }.flowOn(Dispatchers.IO)

    fun getMovieCredits(movieId:Int):Flow<Result<Credit>> =flow{
        val response=api.getMovieCredits(movieId,API_KEY)
        if (response.isSuccessful){
            emit(Result.success(response.body()!!))
        }
        else{
            emit(Result.failure(Throwable()))
        }
    }.flowOn(Dispatchers.IO)

    fun setMovieToFavorite(favoriteMovie: Favorite): Flow<Result<Unit>> = flow{
        val response=api.addMovieToFavorite(API_KEY,SESSION_ID,favoriteMovie)
        if (response.isSuccessful){
            emit(Result.success(response.body()!!))
        }
        else{
            emit(Result.failure(Throwable("Произошла ошибка")))
        }
    }.flowOn(Dispatchers.IO)
}