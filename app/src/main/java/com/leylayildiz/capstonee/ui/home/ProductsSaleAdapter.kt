package com.leylayildiz.capstonee.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.databinding.ItemProductSaleBinding

class ProductsSaleAdapter (
    private val onProductSaleClick : (Int) -> Unit,
    private val onFavClick: (ProductUI) -> Unit
    ): ListAdapter<ProductUI, ProductsSaleAdapter.SaleProductViewHolder>(SaleProducttDiffUtilCallBack()){

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int, ): SaleProductViewHolder {
        return SaleProductViewHolder(
            ItemProductSaleBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            onProductSaleClick,
            onFavClick
        )
    }
    override fun onBindViewHolder(holder : SaleProductViewHolder, position : Int)= holder.bind(getItem(position))

    class SaleProductViewHolder(
        private val binding : ItemProductSaleBinding,
        private val  onProductSaleClick : (Int) -> Unit,
        private val  onFavClick : (ProductUI) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(product : ProductUI) {
            with(binding){
                   tvTitleSale.text = product.title

                ivFavo.setBackgroundResource(
                    if(product.isFav) R.drawable.ic_fav
                    else R.drawable.icon_fav
                )

                Glide.with(ivSale).load(product.imageOne).into(ivSale)

                if (product.saleState == true){

                    tvPricee.text = "${product.price} ₺".toString()
                    tvPricee.setTextColor(Color.RED)
                    tvSalePrice.text = "${product.salePrice} ₺".toString()
                }
                else{
                    tvSalePrice.visibility = View.INVISIBLE
                    tvPricee.text = "${product.salePrice} ₺".toString()
                }
                root.setOnClickListener{
                    onProductSaleClick(product.id)
                }
                ivFavo.setOnClickListener {
                    onFavClick(product)
                }
            }
        }
    }
    class SaleProducttDiffUtilCallBack: DiffUtil.ItemCallback<ProductUI>(){
        override fun areItemsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
            return oldItem == newItem
        }
    }
}