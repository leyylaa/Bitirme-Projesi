package com.leylayildiz.capstonee.data.model.response

data class GetSearchProductsResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)