package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.test_app.domain.repository.PlayerRepository
import com.example.test_app.presentation.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfig: AppBarConfiguration

    private val playerRepository: PlayerRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.test_app.presentation.R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfig = AppBarConfiguration(
            setOf(
                com.example.test_app.presentation.R.id.achievementListFragment,
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfig)

        NavigationUI.setupWithNavController(binding.navigationView, navController)

        lifecycleScope.launch {
            val savedId = playerRepository.getSavedSteamId()

            if (savedId != null) {
                val graph = navController.navInflater.inflate(com.example.test_app.presentation.R.navigation.nav_graph)
                graph.setStartDestination(com.example.test_app.presentation.R.id.achievementListFragment)
                navController.graph = graph
            }
        }
    }
}