
package com.leylayildiz.capstonee.ui.search

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
class SearchViewModel @Inject constructor(
    private val productRepository : ProductRepository,
): ViewModel() {

    private var _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState>
        get() = _searchState
    private var query = ""
    fun getSearchProducts(query: String) {
        viewModelScope.launch {

            _searchState.value = SearchState.Loading

            val result = productRepository.getSearchProducts(query)
            if (result is Resource.Success) {
                _searchState.value = SearchState.SuccessState(result.data)
            } else if (result is Resource.Error) {
                _searchState.value = SearchState.ShowPopUp(result.errorMessage)
            }
        }
    }
    fun addToFavorites(product: ProductUI) {
        viewModelScope.launch {
            productRepository.addToFavorites(product)
        }
    }
}
sealed interface SearchState{
    object Loading : SearchState
    data class SuccessState(val products: List<ProductUI>) :SearchState
    data class EmptyScreen(val failMessage: String) : SearchState
    data class ShowPopUp(val errorMessage: String): SearchState

}

