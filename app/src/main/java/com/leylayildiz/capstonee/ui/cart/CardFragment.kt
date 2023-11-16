package com.example.shoppingapp.presentation.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.data.model.request.ClearCartRequest
import com.leylayildiz.capstonee.databinding.FragmentCardBinding
import com.leylayildiz.capstonee.ui.cart.CardViewModel
import com.leylayildiz.capstonee.ui.cart.CardAdapter
import com.leylayildiz.capstonee.ui.cart.CartFragmentDirections
import com.leylayildiz.capstonee.ui.cart.CartState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card),CardListener {

    private lateinit var binding: FragmentCardBinding
    private val cardAdapter by lazy { CardAdapter(this) }
    private val viewModel by viewModels<CardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        with(binding) {

            recyclerView.adapter = cardAdapter

            deleteCard.setOnClickListener {
                val clearCartRequest = viewModel.currentUser!!.uid?.let { it1 ->
                    ClearCartRequest(
                        it1
                    )
                }
                if (clearCartRequest != null) {
                    viewModel.clearCart(clearCartRequest)
                }
            }

            btnPaymen.setOnClickListener {
                //navigatePaymentScrenn()
            }
        }

        with(viewModel)
        {

            viewModel.currentUser!!.uid?.let { getCartProducts(it) }
        }
    }
    private fun observeData() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->

            when (state) {

                CartState.Loading -> {
                }

                is CartState.CartList -> {
                    cardAdapter.submitList(state.products)
                }
                is CartState.PostResponse -> {
                    Toast.makeText(
                        requireContext(),
                        state.base.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CartState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }
    fun onCardClick(id: Int) {
        val action = CartFragmentDirections.CartToDetail(id)
        findNavController().navigate(action)
    }
    fun onDeleteClick(id: Int) {

        viewModel.deleteFromCart(id)
    }
}