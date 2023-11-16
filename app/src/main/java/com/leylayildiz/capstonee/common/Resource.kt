package com.leylayildiz.capstonee.common

import com.leylayildiz.capstonee.data.model.response.ProductUI

sealed interface Resource<out T: Any>  {
    data class Success<out T: Any>(val data: T):Resource<T>
    data class Error(val errorMessage: String):Resource<Nothing>
    data class Fail(val failMessage: String):Resource<Nothing>

}
