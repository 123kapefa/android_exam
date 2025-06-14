package com.example.test_app.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.test_app.domain.repository.PlayerRepository
import com.example.test_app.presentation.R
import com.example.test_app.presentation.api.DrawerController
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PlayerProfileFragment : Fragment(R.layout.fragment_player_profile) {

    private val repo: PlayerRepository by inject() // Koin

    private lateinit var avatar: ImageView
    private lateinit var name  : TextView
    private lateinit var logoutBtn: MaterialButton

    private val drawerController: DrawerController?
        get() = activity as? DrawerController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(view) {
        super.onViewCreated(view, savedInstanceState)

        avatar    = findViewById(R.id.avatarImageView)
        name      = findViewById(R.id.nameTextView)
        logoutBtn = findViewById(R.id.logoutButton)

        viewLifecycleOwner.lifecycleScope.launch {
            repo.getSavedSteamId()?.let { id ->
                val player = repo.getPlayerInfo(id)
                name.text = player.personaname
                Glide.with(this@PlayerProfileFragment)
                    .load(player.avatarfull)
                    .circleCrop()
                    .into(avatar)
            }
        }

        logoutBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repo.logout()
                findNavController().navigate(
                    R.id.loginFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true)
                        .build()
                )

                drawerController?.closeDrawer()
            }
        }
    }
}