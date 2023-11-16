package com.leylayildiz.capstonee.data.model.response

data class GetProductDetailResponse(
    val product: Product?,
    val rate : Float?
) :BaseResponse()
