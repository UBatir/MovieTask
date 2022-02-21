package uz.example.movie.ui.moview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.example.movie.R
import uz.example.movie.data.model.Favorite
import uz.example.movie.databinding.FragmentMovieBinding
import uz.example.movie.utils.scope

class MovieFragment:Fragment(R.layout.fragment_movie) {

    private val binding by viewBinding(FragmentMovieBinding::bind)
    private val viewModel by viewModel<MovieViewModel>()
    private val safeArgs:MovieFragmentArgs by navArgs()
    private val adapter: MovieAdapter by inject()
    private var isFavorite=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)=binding.scope {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter=adapter
        adapter.models.clear()
        val id=safeArgs.movie.id
        viewModel.getMovieCredits(id)
        setUpObserverActors()
        ivFavorite.setOnClickListener {
            isFavorite=!isFavorite
            setFavoriteIcon(id)
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val glideUrl = GlideUrl("https://image.tmdb.org/t/p/original/${safeArgs.movie.posterPath}")
        Glide.with(root)
            .load(glideUrl)
            .into(ivMovie)
        tvOriginalTitle.text=safeArgs.movie.originalTitle
        tvOverview.text=safeArgs.movie.overview
        tvRank.text=safeArgs.movie.voteAverage.toString()
    }

    private fun setUpObserverActors(){
        viewModel.success.observe(viewLifecycleOwner,{
            adapter.models=it.cast.toMutableList()
        })
        viewModel.error.observe(viewLifecycleOwner,{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setFavoriteIcon(movieId:Int)=binding.scope {
        if(isFavorite){
            viewModel.setMovieToFavorite(Favorite("movie",movieId,true))
            Toast.makeText(requireContext(), "Добавлено в избранные", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.setMovieToFavorite(Favorite("movie",movieId,false))
            Toast.makeText(requireContext(), "Удалено из избранных", Toast.LENGTH_SHORT).show()
        }
    }
}