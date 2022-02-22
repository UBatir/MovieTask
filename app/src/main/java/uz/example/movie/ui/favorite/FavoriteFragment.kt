package uz.example.movie.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.example.movie.R
import uz.example.movie.databinding.FragmentFavoriteBinding
import uz.example.movie.databinding.FragmentMainBinding
import uz.example.movie.ui.main.MainAdapter
import uz.example.movie.ui.main.MainFragmentDirections
import uz.example.movie.ui.main.MainViewModel
import uz.example.movie.utils.scope

class FavoriteFragment:Fragment(R.layout.fragment_favorite) {

    private val viewModel by viewModel<FavoriteViewModel>()
    private val adapter by inject<FavoriteAdapter>()
    private val binding by viewBinding(FragmentFavoriteBinding::bind)

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)=binding.scope {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL)
        )
        viewModel.getFavoriteMovies()
        setUpObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObserver()=binding.scope{
        tvEmpty.visibility=View.GONE
        progressBar.visibility = View.VISIBLE
        viewModel.success.observe(viewLifecycleOwner,{
            progressBar.visibility = View.GONE
            if (it.results.isNotEmpty()) {
                tvEmpty.visibility=View.GONE
                adapter.models=it.results.toMutableList()
            }
            else {
                adapter.models.clear()
                adapter.notifyDataSetChanged()
                tvEmpty.visibility=View.VISIBLE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            progressBar.visibility = View.GONE
        })
    }
}