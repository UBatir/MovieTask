package uz.example.movie.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import uz.example.movie.R
import uz.example.movie.data.model.Movie
import uz.example.movie.databinding.ItemMovieBinding
import uz.example.movie.utils.scope


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var models: List<Movie> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemMovieBinding::bind)

        fun populateModel(model: Movie) = binding.scope {
            tvTitle.text = model.title
            val glideUrl = GlideUrl("https://image.tmdb.org/t/p/original/${model.posterPath}")
            Glide.with(root)
                .load(glideUrl)
                .into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size
}
