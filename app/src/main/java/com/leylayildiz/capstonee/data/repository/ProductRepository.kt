package com.leylayildiz.capstonee.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.leylayildiz.capstonee.common.Resource
import com.leylayildiz.capstonee.data.mapper.mapProductEntitiesToProductUI
import com.leylayildiz.capstonee.data.mapper.mapProductToProductUI
import com.leylayildiz.capstonee.data.mapper.mapProductUIToProductEntity
import com.leylayildiz.capstonee.data.mapper.mapToProductsToProductUI
import com.leylayildiz.capstonee.data.model.request.AddToCartRequest
import com.leylayildiz.capstonee.data.model.request.ClearCartRequest
import com.leylayildiz.capstonee.data.model.request.DeleteFromCartRequest
import com.leylayildiz.capstonee.data.model.response.BaseResponse
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.data.source.local.ProductDao
import com.leylayildiz.capstonee.data.source.remote.ProductService
import com.leylayildiz.capstonee.ui.cart.CartState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProductRepository (
    private val productService : ProductService,
    private val productDao: ProductDao
) {
    suspend fun getProducts() : Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getProducts().body()
            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapToProductsToProductUI(favorites))

            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e : Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
    suspend fun getSaleProducts() : Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getSaleProducts().body()
            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapToProductsToProductUI(favorites))

            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e : Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
    suspend fun getProductDetail(id : Int) : Resource<ProductUI> = withContext(Dispatchers.IO) {

        try {
            val favorites = productDao.getProductIds()
            val response = productService.getProductDetail(id).body()

            if (response?.status == 200 && response.product != null) {
                Resource.Success(response.product.mapProductToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e : Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
    suspend fun getCartProducts(userId: String) : Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getCartProducts(userId).body()
            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapToProductsToProductUI(favorites))

            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e : Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
    suspend fun addToCart(addToCartRequest: AddToCartRequest): Resource<BaseResponse> {
        return try {
            val result = productService.addToCart(addToCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Fail(("Product not added"))
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
    suspend fun deleteFromCart(id: Int): Resource<BaseResponse> =
        try {
            Resource.Success(productService.deleteFromCart(DeleteFromCartRequest(id)))
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }

    suspend fun clearCart(clearCartRequest: ClearCartRequest): Resource<BaseResponse> {

        return try {
            val result = productService.clearCart(clearCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Resource.Fail("Cart not Cleared !").failMessage)
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
        getCartProducts(FirebaseAuth.getInstance().currentUser!!.uid)
    }
        suspend fun getSearchProducts(query:String) : Resource<List<ProductUI>> = withContext(Dispatchers.IO) {

            try {
                val favorites = productDao.getProductIds()
                val response = productService.getSearchProducts(query).body()
                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapToProductsToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e : Exception) {
                Resource.Error(e.message.orEmpty())
            }
    }
    suspend fun getFavoriteProducts(): Resource<List<ProductUI>> {
        return try {
            val result = productDao.getFavoriteProducts().map { it.mapProductToProductUI()}
            if (result.isEmpty()) {
                Resource.Error(Exception("There are no products here!"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
    private suspend fun getFavoriteIds() = productDao.getFavoriteIds()

    suspend fun addToFavorites(productUI : ProductUI) {

        productDao.addProduct(productUI.mapProductUIToProductEntity())
    }
    suspend fun deleteFromFavorites(productUI : ProductUI) {
        productDao.deleteProduct(productUI.mapProductUIToProductEntity())
    }
    suspend fun getFavorites():Resource<List<ProductUI>> =withContext(Dispatchers.IO) {
        try {
            val product = productDao.getFavoriteProducts()
            if (product.isEmpty()) {
                Resource.Fail("Products not found")
            } else {
                Resource.Success(product.mapProductEntitiesToProductUI())
            }
        } catch (e : Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
}



