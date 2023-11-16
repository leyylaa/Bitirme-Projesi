        package com.leylayildiz.capstonee.ui.home
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leylayildiz.capstonee.common.Resource
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository : ProductRepository): ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState : LiveData<HomeState> get() = _homeState


       fun getProducts() = viewModelScope.launch {
        val productsResult = productRepository.getProducts()
        val saleProductsResult = productRepository.getSaleProducts()

        _homeState.value = HomeState.Loading
        _homeState.value = when {
            productsResult is Resource.Success && saleProductsResult is Resource.Success -> {
                HomeState.SuccessState(productsResult.data, saleProductsResult.data)

            }
            productsResult is Resource.Fail   -> {
                HomeState.EmptyScreen(productsResult.failMessage)
            }
            saleProductsResult is Resource.Fail ->{
                HomeState.EmptyScreen(saleProductsResult.failMessage)
            }

            productsResult is Resource.Error -> {
                HomeState.ShowPopUp(productsResult.errorMessage)
            }

            saleProductsResult is Resource.Error -> {
                HomeState.ShowPopUp(saleProductsResult.errorMessage)
            }

            else  -> {
                HomeState.ShowPopUp("Bir Hata Oluştu!!")

            }
        }
    }

    fun setFavoriteState(product:ProductUI) = viewModelScope.launch{
        if (product.isFav){
            productRepository.deleteFromFavorites(product)

        }else{
            productRepository.addToFavorites(product)
        }
        getProducts()
    }
}
sealed interface HomeState{
    object Loading : HomeState
    data class SuccessState(val products: List<ProductUI>, val saleProducts: List<ProductUI>): HomeState
    data class EmptyScreen(val  failMessage: String): HomeState
    data class ShowPopUp(val errorMessage : String): HomeState
}