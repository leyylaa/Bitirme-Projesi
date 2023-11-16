package com.leylayildiz.capstonee.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.databinding.ItemFavProductBinding
import com.leylayildiz.capstonee.databinding.ItemProductBinding


class FavoritesAdapter (
    private val onProductClick : (Int) -> Unit,
    private val onDeleteClick: (ProductUI) -> Unit

): ListAdapter<ProductUI, FavoritesAdapter.FavProductViewHolder>(FavProductDiffUtilCallBack()){
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int, ): FavProductViewHolder {
        return FavProductViewHolder(
            ItemFavProductBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            onProductClick,
            onDeleteClick
        )
    }
    override fun onBindViewHolder(holder : FavProductViewHolder, position : Int)= holder.bind(getItem(position))

     class FavProductViewHolder(
         private val binding : ItemFavProductBinding,
         private val  onProductClick : (Int) -> Unit,
         private val onDeleteClick: (ProductUI) -> Unit

     ): RecyclerView.ViewHolder(binding.root) {

         fun bind(product : ProductUI) {
             with(binding){
                 tvFavTitle.text = product.title
                 tvFavPrice.text = "${product.price} ₺"
                 tvFavSalePrice.text =  "${product.salePrice} ₺".toString()
                // tvPrTitlee.text = product.title
                // tvPrPricee.text= "${product.price} ₺"

                 Glide.with(ivProduct).load(product.imageOne).into(ivProduct)

                 root.setOnClickListener{
                     onProductClick(product.id)
                 }
                 ivDelete.setOnClickListener {
                     onDeleteClick(product)
                 }
             }
         }
     }
    class FavProductDiffUtilCallBack: DiffUtil.ItemCallback<ProductUI>(){
        override fun areItemsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
          return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
           return oldItem == newItem
        }

    }
}