package com.leylayildiz.capstonee.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.invisible
import com.leylayildiz.capstonee.common.viewBinding
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val productsAdapter = ProductsAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)
    private val productsSaleAdapter = ProductsSaleAdapter(onProductSaleClick = ::onProductSaleClick, onFavClick = ::onFavClick)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        with(binding) {
          rvAllProducts.adapter= productsAdapter
          rvSaleProducts.adapter= productsSaleAdapter
        }
        observeData()
    }

    private fun observeData() = with(binding){
        viewModel.homeState.observe(viewLifecycleOwner){ state ->
            when(state){
                HomeState.Loading -> progressBar.visible()

               is HomeState.SuccessState -> {
                   progressBar.invisible()
                   productsAdapter.submitList(state.products)
                   productsSaleAdapter.submitList(state.saleProducts)
               }

               is HomeState.EmptyScreen ->{
                   progressBar.invisible()
                   ivEmpty.visible()
                   tvEmpty.visible()
                   tvEmpty.text = state.failMessage
                }
                is HomeState.ShowPopUp ->{
                  progressBar.invisible()
                    Snackbar.make(requireView(),state.errorMessage,1000).show()

                }
            }
        }
    }
        private fun onProductClick(id: Int) {
         findNavController().navigate(HomeFragmentDirections.HomeToDetail(id))
        }

    private fun onProductSaleClick(id : Int){
        findNavController().navigate(HomeFragmentDirections.HomeToDetail(id))
    }

    private fun onFavClick(product:ProductUI) {
    viewModel.setFavoriteState(product)
    }
}