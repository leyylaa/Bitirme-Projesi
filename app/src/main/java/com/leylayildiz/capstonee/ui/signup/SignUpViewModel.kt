package com.leylayildiz.capstonee.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leylayildiz.capstonee.common.Resource
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.data.repository.AuthRepository
import com.leylayildiz.capstonee.data.repository.ProductRepository
import com.leylayildiz.capstonee.ui.signin.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository : AuthRepository
): ViewModel() {

    private var _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState>get() = _signUpState

    fun SignUp(email:String,password:String)= viewModelScope.launch{
        if (checkFields(email,password)){
            _signUpState.value = SignUpState.Loading
            _signUpState.value = when( val result = authRepository.signUp(email, password)) {
                is Resource.Success -> SignUpState.GoToHome
                is Resource.Fail -> SignUpState.ShowPopUp(result.failMessage)
                is Resource.Error -> SignUpState.ShowPopUp(result.errorMessage)
            }
        }
    }
    private fun checkFields(email : String,password : String): Boolean{
        return when{
            email.isEmpty() -> {
                _signUpState.value = SignUpState.ShowPopUp("E-mail Boş Bırakılamaz!!")
                false
            }
            password.isEmpty() -> {
                _signUpState.value = SignUpState.ShowPopUp("Şifre Alanı Boş Bırakılamaz!!")
                false
            }
            password.length < 6 -> {
                _signUpState.value = SignUpState.ShowPopUp("Şifre Alanı 6 karakterden kısa olamaz!!")
                false
            }
            else -> true
        }
    }
}
sealed interface SignUpState{
    object Loading : SignUpState
  object GoToHome:SignUpState
    data class ShowPopUp(val errorMessage: String): SignUpState

}
