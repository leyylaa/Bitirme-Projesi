package com.leylayildiz.capstonee.data.source.remote

import com.leylayildiz.capstonee.data.model.request.AddToCartRequest
import com.leylayildiz.capstonee.data.model.request.ClearCartRequest
import com.leylayildiz.capstonee.data.model.request.DeleteFromCartRequest
import com.leylayildiz.capstonee.data.model.response.BaseResponse
import com.leylayildiz.capstonee.data.model.response.GetCartProductsResponse
import com.leylayildiz.capstonee.data.model.response.GetProductDetailResponse
import com.leylayildiz.capstonee.data.model.response.GetProductsResponse
import com.leylayildiz.capstonee.data.model.response.GetSaleProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query



interface ProductService {
    @GET("get_products.php")
   suspend fun getProducts(): Response<GetProductsResponse>

    @GET("get_sale_products.php")
    suspend fun getSaleProducts(): Response<GetSaleProductsResponse>


    @GET("get_product_detail.php")
   suspend fun getProductDetail(
    @Query("id") id: Int
    ):Response<GetProductDetailResponse>


   @GET("get_cart_products")
   suspend fun getCartProducts(
    @Query("userId") userId: String
    ): Response<GetCartProductsResponse>


    @GET("get_search_products")
    suspend fun getSearchProducts(
        @Query("query") query: String
    ): Response<GetProductsResponse>


    @Headers("")
    @POST("ADD_TO_CARD")
    suspend fun addToCart(
        @Body addToCartRequest : AddToCartRequest
    ) : BaseResponse

    @Headers("")
    @POST("DELETE_FROM_CARD")
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): BaseResponse

    @Headers("")
    @POST("CLEAR_CART")
    suspend fun clearCart(
        @Body clearCartRequest: ClearCartRequest
    ) : BaseResponse



}





