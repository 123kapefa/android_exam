package com.example.test_app.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_app.presentation.R
import com.example.test_app.presentation.adapter.AchievementAdapter
import com.example.test_app.presentation.viewmodel.AchievementListUiState
import com.example.test_app.presentation.viewmodel.AchievementListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementListFragment : Fragment(R.layout.fragment_achievement_list) {

    private val viewModel: AchievementListViewModel by viewModel()
    private lateinit var adapter: AchievementAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        adapter = AchievementAdapter { id ->
//            val bundle = bundleOf("achievementId" to id)
//            findNavController().navigate(
//                R.id.action_global_to_achievementDetail,
//                bundle
//            )
//        }

        adapter = AchievementAdapter { id ->
            findNavController().navigate(
                R.id.action_list_to_detail,
                bundleOf("achievementId" to id)
            )
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is AchievementListUiState.Loading -> {
                    }
                    is AchievementListUiState.Success -> {
                        adapter.submitList(state.data)
                    }
                    is AchievementListUiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.loadPlayerAchievements("76561199096529705")
    }
}