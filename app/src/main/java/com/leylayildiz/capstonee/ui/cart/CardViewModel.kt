package com.leylayildiz.capstonee.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.leylayildiz.capstonee.common.Resource
import com.leylayildiz.capstonee.data.model.request.ClearCartRequest
import com.leylayildiz.capstonee.data.model.response.BaseResponse
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.data.repository.AuthRepository
import com.leylayildiz.capstonee.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject
constructor(
    private val productRepository: ProductRepository, private val authRepository: AuthRepository):

    ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState
    val currentUser: FirebaseUser?
        get() = authRepository.currentUser()
    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.getCartProducts(userId)
            if (result is Resource.Success) {
                _cartState.value = CartState.CartList(result.data)
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.errorMessage)
            }
        }
    }
    fun deleteFromCart(id: Int) {
        viewModelScope.launch {
            productRepository.deleteFromCart(id)
        }
    }
    fun clearCart(clearCartRequest: ClearCartRequest) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.clearCart(clearCartRequest)
            if (result is Resource.Success) {
                _cartState.value = CartState.PostResponse(result.data)
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.errorMessage)
            }
        }
    }
}
sealed interface CartState {
    object Loading : CartState
    data class PostResponse(val base: BaseResponse) : CartState
    data class CartList(val products: List<ProductUI>) : CartState
    data class Error(val throwable : String) : CartState
}