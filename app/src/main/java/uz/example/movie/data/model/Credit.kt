package uz.example.movie.data.model

data class Credit(
    val id:Int,
    val cast:List<Actor> = listOf()
)
