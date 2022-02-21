package uz.example.movie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.example.movie.domain.MainRepository
import uz.example.movie.data.model.ResponseMovies

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {

    private var mutableSuccess:MutableLiveData<ResponseMovies> = MutableLiveData()
    val success:LiveData<ResponseMovies> = mutableSuccess
    private var mError:MutableLiveData<String> = MutableLiveData()
    val error:LiveData<String> = mError

    fun getMovies(query:String){
        mainRepository.getMovies(query).onEach {
            it.onSuccess { res->
                mutableSuccess.value=res
            }
            it.onFailure { th->
                mError.value=th.message
            }
        }.catch() {
            mError.value = "catch error in viewModel"
        }.launchIn(viewModelScope)
    }

}