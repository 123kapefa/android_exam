package com.example.test_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.test_app.domain.repository.PlayerRepository
import com.example.test_app.presentation.R
import com.example.test_app.presentation.api.DrawerController
import com.example.test_app.presentation.databinding.FragmentPlayerProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class PlayerProfileFragment : Fragment() {

    private val playerRepository: PlayerRepository by inject()

    private var _binding: FragmentPlayerProfileBinding? = null
    private val binding get() = _binding!!

    private val drawerController: DrawerController?
        get() = activity as? DrawerController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val id = playerRepository.getSavedSteamId()
            if (id == null) {
                binding.nameTextView.text = getString(R.string.unknown_player)
                return@launch
            }

            val player = withContext(Dispatchers.IO) {
                playerRepository.getPlayerInfo(id)
            }

            binding.nameTextView.text = player.personaname
            Glide.with(binding.avatarImageView)
                .load(player.avatarfull)
                .circleCrop()
                .into(binding.avatarImageView)
        }

        binding.logoutButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) { playerRepository.logout() }

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}