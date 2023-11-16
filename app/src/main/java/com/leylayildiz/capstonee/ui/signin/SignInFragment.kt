package com.leylayildiz.capstonee.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.invisible
import com.leylayildiz.capstonee.common.viewBinding
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.databinding.FragmentDetailBinding
import com.leylayildiz.capstonee.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnSignIn.setOnClickListener {
                viewModel.SignIn(
                   etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
            tvNewUser.setOnClickListener {
                findNavController().navigate(R.id.SignInToSingUp)
            }
        }
        observeData()
    }
    private fun observeData() = with(binding) {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->

            when (state) {
                SignInState.Loading -> progressBar2.visible()

                is SignInState.GoToHome -> {
                    progressBar2.invisible()
                    findNavController().navigate(R.id.SignInToMainGraph)
                }

                is SignInState.ShowPopUp -> {
                    progressBar2.invisible()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}
