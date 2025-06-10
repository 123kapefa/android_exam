package com.example.test_app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.presentation.R

class AchievementAdapter : ListAdapter<PlayerAchievement, AchievementAdapter.AchievementViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titleTextView)
        private val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val icon: ImageView = itemView.findViewById(R.id.iconImageView)

        fun bind(item: PlayerAchievement) {
            title.text = item.title
            description.text = item.description
            val imageUrl = if (item.isUnlocked) item.iconUrl else item.iconGrayUrl
            Glide.with(itemView.context).load(imageUrl).into(icon)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PlayerAchievement>() {
            override fun areItemsTheSame(oldItem: PlayerAchievement, newItem: PlayerAchievement): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PlayerAchievement, newItem: PlayerAchievement): Boolean {
                return oldItem == newItem
            }
        }
    }
}
