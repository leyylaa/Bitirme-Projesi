package com.leylayildiz.capstonee.ui.detail

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
class DetailViewModel @Inject constructor(private val productRepository : ProductRepository): ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>get() = _detailState


    fun getProductDetail(id :Int)= viewModelScope.launch{
        _detailState.value = DetailState.Loading
        _detailState.value = when(val result = productRepository.getProductDetail(id)) {

            is Resource.Success ->  DetailState.SuccessState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error ->  DetailState.ShowPopUp(result.errorMessage)
        }
    }
}
sealed interface DetailState{
    object Loading : DetailState
    data class SuccessState(val product : ProductUI) :DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowPopUp(val errorMessage: String): DetailState

}
