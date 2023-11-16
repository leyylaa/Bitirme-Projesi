package com.leylayildiz.capstonee.data.model.response

data class GetSaleProductsResponse(
    val products:List<Product>?,
    val status : Int?,
    val message: String?
    
)
