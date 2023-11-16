package com.leylayildiz.capstonee.ui.favorites
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
class FavoritesViewModel @Inject constructor(private val productRepository : ProductRepository): ViewModel() {
  //  private var _favoritesState = MutableLiveData<FavoritesState>()
  //  val favoritesState : LiveData<FavoritesState> get() = _favoritesState
    private var _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> get() = _favoritesState
    fun getFavoriteProducts()= viewModelScope.launch {

          _favoritesState.value = FavoritesState.Loading
          _favoritesState.value = when(val result = productRepository.getFavorites()){
              is Resource.Success -> FavoritesState.SuccessState(result.data,result.data)
              is Resource.Fail -> FavoritesState.EmptyScreen(result.failMessage)
              is Resource.Error -> FavoritesState.ShowPopUp(result.errorMessage)
          }
      }
    fun deleteFromFavorites(product: ProductUI) = viewModelScope.launch{
        productRepository.deleteFromFavorites(product)
        getFavoriteProducts()
    }
}
sealed interface FavoritesState{
    object Loading : FavoritesState
    data class SuccessState(val products: List<ProductUI>, val saleProducts: List<ProductUI>): FavoritesState
    data class EmptyScreen(val  failMessage: String): FavoritesState
    data class ShowPopUp(val errorMessage : String): FavoritesState

    }
