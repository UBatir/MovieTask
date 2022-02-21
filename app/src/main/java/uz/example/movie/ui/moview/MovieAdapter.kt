package uz.example.movie.ui.moview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import uz.example.movie.R
import uz.example.movie.data.model.Actor
import uz.example.movie.databinding.ItemActorBinding
import uz.example.movie.utils.scope


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var models: MutableList<Actor> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemActorBinding::bind)

        fun populateModel(model: Actor) = binding.scope {
            tvName.text = model.name
            val glideUrl = GlideUrl("https://image.tmdb.org/t/p/original/${model.profilePath}")
            Glide.with(root)
                .load(glideUrl)
                .placeholder(R.drawable.no_icon)
                .into(ivActor)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size
}
