package com.leylayildiz.capstonee.data.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("fav_products")
data class ProductEntity(

    @PrimaryKey()
    @ColumnInfo("productId")
    val productId: Int?,

    @ColumnInfo("title")
    val title: String?,

    @ColumnInfo("price")
    val price: Double?,

    @ColumnInfo("salePrice")
    val salePrice: Double?,

    @ColumnInfo("description")
    val description: String?,

    @ColumnInfo("category")
    val category: String?,

    @ColumnInfo("imageOne")
    val imageOne:String?,

    @ColumnInfo("rate")
    val rate:Double?,

    @ColumnInfo("count")
    val count:Int?,

    @ColumnInfo("saleState")
    val saleState:Boolean?,


    )
