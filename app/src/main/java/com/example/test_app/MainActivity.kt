package com.example.test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.test_app.domain.repository.PlayerRepository
import com.example.test_app.presentation.databinding.ActivityMainBinding
import com.example.test_app.presentation.fragment.LoginFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    // берём репозиторий из Koin
    private val playerRepository: PlayerRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.test_app.presentation.R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        // привязываем NavigationView к NavController
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        // определяем стартовый экран
        lifecycleScope.launch {
            val savedId = playerRepository.getSavedSteamId()

            if (savedId != null) {
                val graph = navController.navInflater.inflate(R.navigation.nav_graph)
                graph.setStartDestination(R.id.achievementListFragment)
                navController.graph = graph
            }
        }
    }
}