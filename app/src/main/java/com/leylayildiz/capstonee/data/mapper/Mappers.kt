package com.leylayildiz.capstonee.data.mapper

import com.leylayildiz.capstonee.data.model.response.Product
import com.leylayildiz.capstonee.data.model.response.ProductEntity
import com.leylayildiz.capstonee.data.model.response.ProductUI


fun Product.mapProductToProductUI(favorites:List<Int>) =
        ProductUI(
            id = id ?: 1,
            title = title.orEmpty(),
            price = price ?:0.0,
            salePrice = salePrice ?:0.0,
            description = description.orEmpty(),
            category = category.orEmpty(),
            imageOne = imageOne.orEmpty(),
            rate = rate ?:0.0,
            count = count ?: 0,
            saleState = saleState ?: false,
            isFav = favorites.contains(id)

            )

  fun List<Product>.mapToProductsToProductUI(favorites:List<Int>) =
  map {
      ProductUI(
          id = it.id ?:1,
          title = it.title.orEmpty(),
          price = it.price ?:0.0,
          salePrice = it.salePrice ?:0.0,
          description = it.description.orEmpty(),
          category = it.category.orEmpty(),
          imageOne = it.imageOne.orEmpty(),
          rate = it.rate ?:0.0,
          count = it.count ?: 0,
          saleState = it.saleState ?:false,
          isFav = favorites.contains(it.id)

      )

  }

fun ProductUI.mapProductUIToProductEntity() =
    ProductEntity(
        productId = id,
        title = title,
        price = price,
        salePrice = salePrice,
        description = description,
        category = category,
        imageOne = imageOne,
        rate = rate,
        count = count,
        saleState = saleState
    )

fun List<ProductEntity>.mapProductEntitiesToProductUI()=
    map {
        ProductUI(
        id = it.productId ?:1,
        title = it.title.orEmpty(),
        price = it.price ?:0.0,
        salePrice = it.salePrice ?:0.0,
        description =it. description.orEmpty(),
        category = it.category.orEmpty(),
        imageOne = it.imageOne.orEmpty(),
        rate = it.rate ?:0.0,
        count = it.count ?:0,
        saleState = it.saleState ?:false
        )

    }



