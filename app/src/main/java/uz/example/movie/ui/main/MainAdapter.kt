package uz.example.movie.ui.main

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


class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var models: MutableList<Movie> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onClickItem:(movie:Movie) -> Unit = {}
    fun setOnClickItem(onClickItem:(movie:Movie) -> Unit){
        this.onClickItem=onClickItem
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemMovieBinding::bind)

        fun populateModel(model: Movie) = binding.scope {
            tvTitle.text = model.title
            val glideUrl = GlideUrl("https://image.tmdb.org/t/p/original/${model.posterPath}")
            Glide.with(root)
                .load(glideUrl)
                .into(imageView)
            root.setOnClickListener {
                onClickItem.invoke(model)
            }
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
