package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.test_app.domain.repository.PlayerRepository
import com.example.test_app.presentation.api.DrawerController
import com.example.test_app.presentation.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), DrawerController {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfig: AppBarConfiguration

    private val playerRepository: PlayerRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHost = supportFragmentManager
            .findFragmentById(com.example.test_app.presentation.R.id.fragment_container) as NavHostFragment
        val navController = navHost.navController

        appBarConfig = AppBarConfiguration(
            setOf(
                com.example.test_app.presentation.R.id.achievementListFragment,
                com.example.test_app.presentation.R.id.playerProfileFragment
            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfig)

        NavigationUI.setupWithNavController(
            binding.navigationView,
            navController
        )

        lifecycleScope.launch {
            playerRepository.getSavedSteamId()?.let {
                val graph = navController.navInflater.inflate(com.example.test_app.presentation.R.navigation.nav_graph)
                graph.setStartDestination(com.example.test_app.presentation.R.id.achievementListFragment)
                navController.graph = graph
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController =
            (supportFragmentManager.findFragmentById(com.example.test_app.presentation.R.id.fragment_container) as NavHostFragment)
                .navController
        return NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp()
    }

    override fun closeDrawer() = binding.drawerLayout.close()
    override fun openDrawer()  = binding.drawerLayout.open()
}