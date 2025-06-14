package com.example.test_app.presentation.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.test_app.presentation.R
import com.example.test_app.presentation.viewmodel.AchievementDetailUiState
import com.example.test_app.presentation.viewmodel.AchievementDetailViewModel
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class AchievementDetailFragment : Fragment(R.layout.fragment_achievement_detail) {

    private val achievementId: String by lazy {
        requireArguments()
            .getString("achievementId")
            ?: error("achievementId argument is missing")
    }

    private val viewModel: AchievementDetailViewModel by viewModel {
        parametersOf(achievementId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val icon        = view.findViewById<ImageView>(R.id.iconImageView)
        val title       = view.findViewById<TextView>(R.id.titleTextView)
        val date        = view.findViewById<TextView>(R.id.dateTextView)
        val desc        = view.findViewById<TextView>(R.id.descTextView)
        val badge       = view.findViewById<TextView>(R.id.stateBadge)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val dateFmt = DateTimeFormatter
            .ofPattern("dd MMM yyyy • HH:mm", Locale.getDefault())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is AchievementDetailUiState.Loading -> {
                        progressBar.isVisible = true
                    }

                    is AchievementDetailUiState.Success -> {
                        progressBar.isGone = true
                        val a = state.data

                        title.text = a.title
                        desc.text  = a.description ?: getString(R.string.no_description)

                        if (a.isUnlocked) {
                            date.text = getString(
                                R.string.unlocked_at,
                                dateFmt.format(
                                    Instant.ofEpochSecond(a.unlockTime!!)
                                        .atZone(ZoneId.systemDefault())
                                )
                            )
                        } else {
                            date.text = getString(R.string.not_unlocked_yet)
                        }

                        val color = ContextCompat.getColor(
                            requireContext(),
                            if (a.isUnlocked) R.color.green_500 else R.color.red_400
                        )

                        val bg = (badge.background as? GradientDrawable)
                            ?: ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.rounded_rect
                            )!!.also { badge.background = it } as GradientDrawable

                        bg.setColor(color)
                        badge.text = if (a.isUnlocked)
                            getString(R.string.unlocked)
                        else
                            getString(R.string.locked)

                        Glide.with(icon)
                            .load(if (a.isUnlocked) a.iconUrl else a.iconGrayUrl)
                            .into(icon)
                    }

                    is AchievementDetailUiState.Error -> {
                        progressBar.isGone = true
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
}