package com.leylayildiz.capstonee.ui.favorites

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
import com.leylayildiz.capstonee.databinding.FragmentFavoritesBinding
import com.leylayildiz.capstonee.databinding.FragmentHomeBinding
import com.leylayildiz.capstonee.ui.home.ProductsAdapter
import com.leylayildiz.capstonee.ui.home.ProductsSaleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel by viewModels<FavoritesViewModel>()
    private val productsAdapter = FavoritesAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)
    private val productsSaleAdapter = FavoritesAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteProducts()

        with(binding) {

           rvFavProducts.adapter= productsAdapter

        }
        observeData()
    }

    private fun observeData() = with(binding){
        viewModel.favoritesState.observe(viewLifecycleOwner){ state ->
            when(state){
                FavoritesState.Loading -> {
                }
               is FavoritesState.SuccessState -> {
                   binding.rvFavProducts.visibility = View.VISIBLE
                   productsAdapter.submitList(state.products)
                   productsSaleAdapter.submitList(state.saleProducts)
               }

               is FavoritesState.EmptyScreen ->{

                   ivEmpty2.visible()
                   tvEmpty2.visible()
                   rvFavProducts.invisible()
                   tvEmpty2.text = state.failMessage


                }
                is FavoritesState.ShowPopUp ->{
                 // prBar.invisible()
                    Snackbar.make(requireView(),state.errorMessage,1000).show()

                }
            }
        }
    }
        private fun onProductClick(id: Int) {
         findNavController().navigate(FavoritesFragmentDirections.FavoritesToDetail(id))
        }
   // private fun onProductSaleClick(id: Int) {
       // findNavController().navigate(FavoritesFragmentDirections.FavoritesToDetail(id))
  //  }


    private fun onDeleteClick(product:ProductUI) {
    viewModel.deleteFromFavorites(product)
    }
}