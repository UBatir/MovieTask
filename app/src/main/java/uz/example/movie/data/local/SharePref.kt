package uz.example.movie.data.local

import android.content.Context
import android.content.SharedPreferences
import uz.example.movie.utils.BooleanPreference
import uz.example.movie.utils.StringPreference

class SharePref(context: Context) {
    private val sharePref:SharedPreferences = context.getSharedPreferences("sharePref",Context.MODE_PRIVATE)

    var token : String by StringPreference(sharePref, "")
    var sessionId : String by StringPreference(sharePref, "")
    var firstLaunch : Boolean by BooleanPreference(sharePref, false)
}