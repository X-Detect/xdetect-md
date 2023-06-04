package com.zain.xdetect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zain.xdetect.R
import com.zain.xdetect.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        setBottomNav()

    }

    private fun setBottomNav() {
        val bottomNavView: BottomNavigationView = binding.bottomNavView
        val bottomNavController = findNavController(R.id.bottom_nav_host_fragment)
        bottomNavController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.tvToolbarTitle.text = destination.label
        }
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_article,
                R.id.navigation_history,
                R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(bottomNavController,appBarConfiguration)

        bottomNavView.setupWithNavController(bottomNavController)
    }

    private fun setToolBar() {
        val toolbar: Toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)

        binding.toolbar.btnBackToolbar.visibility = View.INVISIBLE
    }
}