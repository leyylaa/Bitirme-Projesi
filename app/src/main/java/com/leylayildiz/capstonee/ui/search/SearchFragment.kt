package com.leylayildiz.capstonee.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.invisible
import com.leylayildiz.capstonee.common.viewBinding
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchProductListener{

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    private val searchProductsAdapter by lazy { SearchAdapter(this) }
    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearchProducts.adapter = searchProductsAdapter

        binding. searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.getSearchProducts(p0.toString())
                return false
            }
        })
     viewModel.getSearchProducts(query = String())
        observeData()
    }
    private fun observeData() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.Loading -> progressBar.visible()

                is SearchState.SuccessState -> {
                    searchProductsAdapter.submitList(state.products)
                    progressBar.invisible()
                }
                is SearchState.EmptyScreen -> {
                    progressBar.invisible()

                }
                is SearchState.ShowPopUp -> {
                    progressBar.invisible()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    override fun onProductClick(id : Int) {
        val action = SearchFragmentDirections.SearchToDetail(id)
        findNavController().navigate(action)
    }
}
