package com.leylayildiz.capstonee.ui.signup

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
import com.leylayildiz.capstonee.databinding.FragmentSignUpBinding
import com.leylayildiz.capstonee.ui.signin.SignInState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnSignUp.setOnClickListener {
                viewModel.SignUp(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
        observeData()
    }
    private fun observeData() = with(binding) {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->

            when (state) {
                SignUpState.Loading -> progressBar2.visible()

                is SignUpState.GoToHome -> {
                    progressBar2.invisible()
                    findNavController().navigate(R.id.SignUpToMainGraph)
                }

                is SignUpState.ShowPopUp -> {
                    progressBar2.invisible()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()

                }
            }
        }
    }
}
