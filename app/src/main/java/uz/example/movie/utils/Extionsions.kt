package uz.example.movie.utils

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import uz.example.movie.BuildConfig
import androidx.viewbinding.ViewBinding
import com.readystatesoftware.chuck.ChuckInterceptor

fun OkHttpClient.Builder.addLoggingInterceptor(context:Context): OkHttpClient.Builder {
    HttpLoggingInterceptor.Level.HEADERS
    val logging = HttpLoggingInterceptor.Logger { message -> Log.d("HTTP",message) }
    if (BuildConfig.LOGGING) {
        addInterceptor(ChuckInterceptor(context))
        addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}