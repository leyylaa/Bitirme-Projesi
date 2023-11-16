package com.leylayildiz.capstonee.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.loadImage
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.databinding.CardItemBinding
import com.leylayildiz.capstonee.databinding.ItemProductBinding


class CardAdapter (
    private val cardListener : CardListener,

): ListAdapter<ProductUI, CardViewHolder>(CardDiffUtilCallBack()){
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int, ): CardViewHolder =
        CardViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),cardListener)

    override fun onBindViewHolder(holder : CardViewHolder, position : Int)= holder.bind(getItem(position))
}
     class CardViewHolder(
         private val binding : CardItemBinding,
         private val cardListener : CardListener

     ): RecyclerView.ViewHolder(binding.root) {

         fun bind(product : ProductUI) {

             with(binding){
                 cardTitle.text= product.title
                 cardImage.loadImage(product.imageOne)

                 if(product.saleState == true)
                 {
                     salePrice.visibility = View.VISIBLE
                     salePrice.text = "${product.salePrice} ₺"
                     cardPrice.setTextColor(Color.RED)
                 }

                 else
                 {
                     salePrice.visibility = View.I
                     cardPrice.text = "${product.price} ₺"

                 }
                 remove.setOnClickListener {
                     cardListener.onDeleteClick(product.id ?:1)
                 }

                 root.setOnClickListener {
                     cardListener.onCardClick(product.id ?:1)
                 }
             }
         }
     }
    class CardDiffCallBack: DiffUtil.ItemCallback<ProductUI>(){
        override fun areItemsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
          return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
           return oldItem == newItem
        }
    }
interface CardListener{
    fun onCardClick(id:Int)
    fun onDeleteClick(id: Int)
}

