package uz.example.movie.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.example.movie.BuildConfig.BASE_URL
import uz.example.movie.data.local.SharePref
import uz.example.movie.data.remote.ApiInterface
import uz.example.movie.domain.MainRepository
import uz.example.movie.ui.favorite.FavoriteAdapter
import uz.example.movie.ui.favorite.FavoriteViewModel
import uz.example.movie.ui.main.MainAdapter
import uz.example.movie.ui.main.MainViewModel
import uz.example.movie.ui.moview.MovieAdapter
import uz.example.movie.ui.moview.MovieViewModel
import uz.example.movie.utils.addLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val timeOut = 50L

val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }



    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addLoggingInterceptor(androidApplication().applicationContext)
            .connectTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .readTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeout = timeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()

    }



    single {
        get<Retrofit>().create(ApiInterface::class.java)
    }

}

val helperModule = module {
    single { SharePref(androidApplication().applicationContext) }
}

val repositoryModule = module {
    single { MainRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}

val adapterModule = module {
    single { MainAdapter() }
    single { MovieAdapter() }
    single { FavoriteAdapter() }
}
