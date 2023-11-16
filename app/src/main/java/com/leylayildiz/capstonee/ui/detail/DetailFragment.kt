package com.leylayildiz.capstonee.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.invisible
import com.leylayildiz.capstonee.common.viewBinding
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      viewModel.getProductDetail(args.id)

        observeData()
    }
    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->

            when (state) {
                DetailState.Loading -> progressBar.visible()

                is DetailState.SuccessState -> {
                    progressBar.invisible()
                    Glide.with(ivProduct).load(state.product.imageOne).into(ivProduct)
                    tvTitle.text = state.product.title
                    tvSalePricee.text= "${state.product.salePrice} ₺".toString()
                    tvPrice.text = "${state.product.price} ₺"
                    tvDescription.text = state.product.description
                }
                is DetailState.EmptyScreen -> {
                    progressBar.invisible()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }
                is DetailState.ShowPopUp -> {
                    progressBar.invisible()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()

                }
            }
        }
    }
}
