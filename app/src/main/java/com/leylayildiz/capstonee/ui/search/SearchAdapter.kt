package com.leylayildiz.capstonee.ui.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leylayildiz.capstonee.common.loadImage
import com.leylayildiz.capstonee.common.setStrikeThrough
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.data.model.response.ProductUI
import com.leylayildiz.capstonee.databinding.ItemSearchBinding


class SearchAdapter(
    private val searchProductListener: SearchProductListener
): ListAdapter<ProductUI, SearchAdapter.SearchViewHolder>(SearchDiffUtilCallBack()){
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int, ): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            searchProductListener)
    }

    override fun onBindViewHolder(holder : SearchViewHolder, position : Int)= holder.bind(getItem(position))

    class SearchViewHolder(
        private val binding : ItemSearchBinding ,
        private val searchProductListener: SearchProductListener

    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvSearchTitle.text = product.title
            tvSearchPrice.text = "${product.price} ₺"

            if (product.saleState) {
                tvSearchSalePrice.text = "${product.salePrice} ₺"
                tvSearchSalePrice.visible()
                tvSearchPrice.setStrikeThrough()
            }
            ivSearch.loadImage(product.imageOne)

            root.setOnClickListener {
                searchProductListener.onProductClick(product.id)
            }
        }
    }
    class SearchDiffUtilCallBack: DiffUtil.ItemCallback<ProductUI>(){
        override fun areItemsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem : ProductUI, newItem : ProductUI) : Boolean {
            return oldItem == newItem
        }
    }
}

interface SearchProductListener {
    fun onProductClick(id: Int)
}