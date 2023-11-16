package com.leylayildiz.capstonee.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.invisible
import com.leylayildiz.capstonee.common.viewBinding
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.databinding.FragmentSplashBinding
import com.leylayildiz.capstonee.ui.signup.SignUpState
import com.leylayildiz.capstonee.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }
    private fun observeData()  {
        viewModel.splashState.observe(viewLifecycleOwner) { state ->

            when (state) {

                SplashState.GoToSignIn -> {
                    findNavController().navigate(R.id.SplashToSignIn)
                }
              is SplashState.GoToHome -> {
                  findNavController().navigate(R.id.SplashToMainGraph)

                }
            }
        }
    }
}