package com.example.test_app.presentation.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.test_app.presentation.R
import com.example.test_app.presentation.databinding.FragmentAchievementDetailBinding
import com.example.test_app.presentation.viewmodel.AchievementDetailUiState
import com.example.test_app.presentation.viewmodel.AchievementDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class AchievementDetailFragment : Fragment() {

    private var _binding: FragmentAchievementDetailBinding? = null
    private val binding get() = _binding!!

    private val achievementId: String by lazy {
        requireArguments().getString("achievementId")
            ?: error("achievementId argument is missing")
    }

    private val viewModel: AchievementDetailViewModel by viewModel {
        parametersOf(achievementId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAchievementDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateFmt = DateTimeFormatter.ofPattern(
            "dd MMM yyyy • HH:mm",
            Locale.getDefault()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is AchievementDetailUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is AchievementDetailUiState.Success -> {
                        binding.progressBar.isGone = true
                        val a = state.data

                        binding.titleTextView.text = a.title
                        binding.descTextView.text =
                            a.description ?: getString(R.string.no_description)

                        binding.dateTextView.text =
                            if (a.isUnlocked) getString(
                                R.string.unlocked_at,
                                dateFmt.format(
                                    Instant.ofEpochSecond(a.unlockTime!!)
                                        .atZone(ZoneId.systemDefault())
                                )
                            ) else getString(R.string.not_unlocked_yet)

                        val badgeColor = ContextCompat.getColor(
                            requireContext(),
                            if (a.isUnlocked) R.color.green_500 else R.color.red_400
                        )

                        val bg = (binding.stateBadge.background as? GradientDrawable)
                            ?: ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.rounded_rect
                            )!!.also { binding.stateBadge.background = it } as GradientDrawable

                        bg.setColor(badgeColor)
                        binding.stateBadge.text =
                            if (a.isUnlocked) getString(R.string.unlocked)
                            else getString(R.string.locked)

                        Glide.with(binding.iconImageView)
                            .load(if (a.isUnlocked) a.iconUrl else a.iconGrayUrl)
                            .into(binding.iconImageView)
                    }

                    is AchievementDetailUiState.Error -> {
                        binding.progressBar.isGone = true
                        Toast.makeText(
                            requireContext(),
                            state.msg.ifBlank { getString(R.string.something_went_wrong) },
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}