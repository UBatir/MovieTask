package uz.example.movie.ui.moview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.example.movie.data.model.Credit
import uz.example.movie.data.model.Favorite
import uz.example.movie.data.model.Movie
import uz.example.movie.data.model.ResponseMovies
import uz.example.movie.domain.MainRepository

class MovieViewModel(private val mainRepository:MainRepository): ViewModel() {

    private var mutableSuccess: MutableLiveData<Credit> = MutableLiveData()
    val success: LiveData<Credit> = mutableSuccess
    private var mError: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = mError

    fun getMovieCredits(movieId:Int){
        mainRepository.getMovieCredits(movieId).onEach {
            it.onSuccess { m->
                mutableSuccess.value=m
            }
            it.onFailure { th->
                mError.value=th.localizedMessage
            }
        }.launchIn(viewModelScope)
    }

    fun setMovieToFavorite(favoriteMovie:Favorite){
        mainRepository.setMovieToFavorite(favoriteMovie).launchIn(viewModelScope)
    }
}