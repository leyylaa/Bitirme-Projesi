package com.leylayildiz.capstonee.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.leylayildiz.capstonee.MainApplication
import com.leylayildiz.capstonee.R
import com.leylayildiz.capstonee.common.invisible
import com.leylayildiz.capstonee.common.viewBinding
import com.leylayildiz.capstonee.common.visible
import com.leylayildiz.capstonee.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.cartFragment,
                R.id.favoritesFragment
                ->  binding.bottomNavView.visible()
                else -> binding.bottomNavView.invisible()
            }
        }
    }
}