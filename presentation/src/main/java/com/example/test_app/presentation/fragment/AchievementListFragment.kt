package com.example.test_app.presentation.fragment

import AchievementAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_app.presentation.R
import com.example.test_app.presentation.databinding.FragmentAchievementListBinding
import com.example.test_app.presentation.viewmodel.AchievementListUiState
import com.example.test_app.presentation.viewmodel.AchievementListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementListFragment : Fragment() {

    private var _binding: FragmentAchievementListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AchievementListViewModel by viewModel()
    private lateinit var adapter: AchievementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAchievementListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AchievementAdapter { id ->
            findNavController().navigate(
                R.id.action_list_to_detail,
                bundleOf("achievementId" to id)
            )
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AchievementListFragment.adapter
        }

        // наблюдаем стейт-флоу
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is AchievementListUiState.Loading  -> {}
                    is AchievementListUiState.Success  ->
                        adapter.submitList(state.data)
                    is AchievementListUiState.Error    ->
                        Toast.makeText(
                            requireContext(),
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}