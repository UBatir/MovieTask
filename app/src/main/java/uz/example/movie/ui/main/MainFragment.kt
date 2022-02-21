package uz.example.movie.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.example.movie.R
import uz.example.movie.data.local.SharePref
import uz.example.movie.databinding.FragmentMainBinding
import uz.example.movie.utils.scope

class MainFragment:Fragment(R.layout.fragment_main) {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter by inject<MainAdapter>()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private var textChangedJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        ))
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(query: Editable?) {
                query?.let {
                    val searchText = it.toString()
                    textChangedJob?.cancel()
                    textChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(500L)
                        viewModel.getMovies(searchText)
                        setUpObserver()
                    }
                }
            }
        })
        adapter.setOnClickItem {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMovieFragment(it))
        }
    }

    private fun setUpObserver()=binding.scope{
        tvEmpty.visibility=View.GONE
        progressBar.visibility = View.VISIBLE
        viewModel.success.observe(viewLifecycleOwner,{
            progressBar.visibility = View.GONE
            adapter.models=it.results
        })
        viewModel.error.observe(viewLifecycleOwner, {
            progressBar.visibility = View.GONE
        })
    }



    override fun onDestroy() {
        textChangedJob?.cancel()
        super.onDestroy()
    }
}