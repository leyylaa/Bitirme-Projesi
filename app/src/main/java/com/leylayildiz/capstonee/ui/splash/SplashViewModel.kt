package com.leylayildiz.capstonee.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leylayildiz.capstonee.common.Resource
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.data.repository.AuthRepository
import com.leylayildiz.capstonee.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository : AuthRepository
): ViewModel() {

    private var _splashState = MutableLiveData<SplashState>()
    val splashState: LiveData<SplashState>get() = _splashState

    init {
        viewModelScope.launch {
            delay(3000)
            checkUser()
        }
    }
    private fun checkUser() = viewModelScope.launch {
        _splashState.value = if(authRepository.isUserLoggedIn()){
            SplashState.GoToHome
        }else{
            SplashState.GoToSignIn
        }
    }
}
sealed interface SplashState{
    object GoToSignIn :SplashState
    object GoToHome: SplashState


}
